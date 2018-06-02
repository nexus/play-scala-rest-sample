package services

import java.time.LocalDate

import controllers.api.v1.adverts.{CreateData, UpdateData}
import javax.inject.{Inject, Singleton}
import models.{CarAdvert, CarAdvertNew, CarAdvertOld, Fuel}
import storages.CarAdvertsStorage
import utils.Conversions._

trait CarAdvertsService {
  def list(sortBy: String): Seq[CarAdvert]
  def get(id: Int): Option[CarAdvert]
  def create(ad: CreateData): Int
  def update(id: Int, ad: UpdateData): Boolean
  def delete(id: Int): Boolean
}

@Singleton
class CarAdvertsServiceImpl @Inject() (storage: CarAdvertsStorage) extends CarAdvertsService {

  override def list(sortBy: String): Seq[CarAdvert] = {
    storage.list(sortBy)
  }

  override def get(id: Int): Option[CarAdvert] = {
    storage.get(id)
  }

  override def create(data: CreateData): Int = {
    val ad = if (data.isNew)
      CarAdvertNew(None, data.title, Fuel.withName(data.fuel), data.price)
    else
      CarAdvertOld(None, data.title, Fuel.withName(data.fuel), data.price, data.mileage ?? 0, LocalDate.parse(data.firstReg ?? "1980-01-01"))

    storage.create(ad)
  }

  override def update(id: Int, data: UpdateData): Boolean = {
    val foundOpt = storage.get(id)

    val result = foundOpt match {
      case None => throw new Exception("Advert is not found")
      case Some(found) => {
        val ad = found match {
          case a: CarAdvertNew =>
            CarAdvertNew(
              a.id,
              data.title ?? a.title,
              Fuel.withNameOpt(data.fuel ?? "") ?? a.fuel,
              data.price ?? a.price
            )
          case b: CarAdvertOld =>
            CarAdvertOld(b.id,
              data.title ?? b.title,
              Fuel.withNameOpt(data.fuel ?? "") ?? b.fuel,
              data.price ?? b.price,
              data.mileage ?? b.mileage,
              LocalDate.parse(data.firstReg ?? b.firstReg.toString)
            )
        }

        storage.update(ad)
      }
    }

    result
  }

  override def delete(id: Int): Boolean = {
    storage.delete(id)
  }
}