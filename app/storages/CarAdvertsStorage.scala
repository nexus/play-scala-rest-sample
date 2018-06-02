package storages

import java.time.LocalDate

import javax.inject.{Inject, Singleton}
import models.{CarAdvert, CarAdvertNew, CarAdvertOld, Fuel}
import play.api.Configuration

trait CarAdvertsStorage {
  def list(sortBy: String): Seq[CarAdvert]
  def get(id: Int): Option[CarAdvert]
  def create(ad: CarAdvert): CarAdvert
  def update(ad: CarAdvert): Int
  def delete(id: Int): Int
}

@Singleton
class CarAdvertsStorageBaseImpl @Inject() (config: Configuration) extends CarAdvertsStorage {
  override def list(sortBy: String) = {
    val newAd = CarAdvertNew(Some(1), "AUDI A4 avant", Fuel.Gasoline, 10000)

    val oldCarReg = LocalDate.parse("2017-01-01")
    val oldAd = CarAdvertOld(Some(2), "AUDI A6 avant", Fuel.Gasoline, 10000, 5320, oldCarReg)

    val ads = List[CarAdvert](newAd, oldAd)

    ads
  }

  override def get(id: Int) = {
    None
  }

  override def create(ad: CarAdvert) = {
    null
  }

  override def update(ad: CarAdvert) = {
    0
  }

  override def delete(id: Int) = {
    0
  }
}


