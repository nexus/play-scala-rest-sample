package storages

import java.sql.{ResultSet, Statement}
import java.time.LocalDate

import javax.inject.{Inject, Singleton}
import models.{CarAdvert, CarAdvertNew, CarAdvertOld, Fuel}
import play.api.db.Database
import utils.Conversions._

trait CarAdvertsStorage {
  def list(sortBy: String): Seq[CarAdvert]
  def get(id: Int): Option[CarAdvert]
  def create(ad: CarAdvert): Int
  def update(ad: CarAdvert): Boolean
  def delete(id: Int): Boolean
}

@Singleton
class CarAdvertsStorageH2Impl @Inject()(dao: DAO) extends CarAdvertsStorage {

  override def list(sortBy: String) = {
    val sql = s"SELECT * FROM car_adverts ORDER BY $sortBy"
    val ads = dao.select(sql, buildAdvertFromResultSet)
    ads
  }

  override def get(id: Int) = {
    val sql = s"SELECT * FROM car_adverts WHERE id=$id"
    val ads = dao.select(sql, buildAdvertFromResultSet)

    ads.headOption
  }

  override def create(ad: CarAdvert) = {
    val (mileage, firstReg) = ad match {
      case a: CarAdvertNew => (None, None)
      case b: CarAdvertOld => (Some(b.mileage), Some(b.firstReg))
    }

    val mileageStringOpt = mileage.map(m => s"$m")
    val firstRegStringOpt = firstReg.map(d => s"'$d'")

    val sql = s"""
        INSERT INTO car_adverts(title, fuel, price, isNew, mileage, firstReg)
        VALUES ('${ad.title}', '${ad.fuel}', ${ad.price}, ${ad.isNew}, ${ mileageStringOpt ?? "null" }, ${firstRegStringOpt ?? "null"});
      """
    dao.insert(sql)
  }

  override def update(ad: CarAdvert) = {
    val (mileage, firstReg) = ad match {
      case a: CarAdvertNew => (None, None)
      case b: CarAdvertOld => (Some(b.mileage), Some(b.firstReg))
    }

    val mileageStringOpt = mileage.map(m => s"$m")
    val firstRegStringOpt = firstReg.map(d => s"'$d'")

    val sql = s"""
        UPDATE car_adverts
        SET
          title = '${ad.title}',
          fuel = '${ad.fuel}',
          price = ${ad.price},
          isNew = ${ad.isNew},
          mileage = ${ mileageStringOpt ?? "null" },
          firstReg = ${firstRegStringOpt ?? "null"}
        WHERE id = ${ad.id ?? 0};
      """
    dao.exec(sql)
  }

  override def delete(id: Int) = {
    val sql = s"DELETE FROM car_adverts WHERE id=$id"
    dao.exec(sql)
  }

  private def buildAdvertFromResultSet(rs: ResultSet): CarAdvert = {
    val isNew = rs.getBoolean("isNew")
    val id = rs.getInt("id")
    val title = rs.getString("title")
    val fuel = rs.getString("fuel")
    val price = rs.getInt("price")

    val ad = if (isNew) {
      CarAdvertNew(Some(id), title, Fuel.withName(fuel), price)
    } else {
      val mileage = rs.getInt("mileage")
      val firstReg = rs.getString("firstReg")
      CarAdvertOld(Some(id), title, Fuel.withName(fuel), price, mileage, LocalDate.parse(firstReg))
    }

    ad
  }

}

@Singleton
class DAO @Inject()(db: Database) {
  def select[T](sql: String, f: ResultSet => T): Seq[T] = {
    db.withConnection { conn =>
      val statement = conn.prepareStatement(sql)
      val resultSet = statement.executeQuery()
      val iterator = toIterator(resultSet)(f)

      iterator.toList
    }
  }

  def exec[T](sql: String): Boolean = {
    db.withConnection { conn =>
      val statement = conn.prepareStatement(sql)
      statement.executeUpdate() > 0
    }
  }

  def insert[T](sql: String): Int = db.withConnection { conn =>
    val statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
    statement.executeUpdate()
    val generatedKeys = statement.getGeneratedKeys
    if (generatedKeys.next()) {
      generatedKeys.getInt(1)
    }
    else {
      0
    }
  }

  private def toIterator[T](resultSet: ResultSet)(f: ResultSet => T) = {
    new Iterator[T] {
      def hasNext = resultSet.next()

      def next() = f(resultSet)
    }
  }
}

