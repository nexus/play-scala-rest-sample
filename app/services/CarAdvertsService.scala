package services

import java.time.LocalDate

import controllers.api.v1.adverts.{CreateData, UpdateData}
import javax.inject.{Inject, Singleton}
import models.{CarAdvert, CarAdvertNew, CarAdvertOld, Fuel}
import storages.CarAdvertsStorage

trait CarAdvertsService {
  def list(sortBy: String): Seq[CarAdvert]
  def get(id: Int): Option[CarAdvert]
  def create(ad: CreateData): CarAdvert
  def update(id: Int, ad: UpdateData): Int
  def delete(id: Int): Int
}

@Singleton
class CarAdvertsServiceImpl @Inject() (storage: CarAdvertsStorage) extends CarAdvertsService {

  override def list(sortBy: String): Seq[CarAdvert] = {
    storage.list(sortBy)
  }

  override def get(id: Int): Option[CarAdvert] = {
    storage.get(id)
  }

  override def create(data: CreateData): CarAdvert = {
    // TODO creation logic
    val ad = if (data.isNew)
      CarAdvertNew(None, "AUDI A4 avant", Fuel.gasoline, 10000)
    else
      CarAdvertOld(None, "AUDI A6 avant", Fuel.gasoline, 10000, 5320, LocalDate.parse("2017-01-01"))

    storage.create(ad)
  }

  override def update(id: Int, data: UpdateData): Int = {
    // TODO creation logic
    val ad = CarAdvertNew(None, "AUDI A4 avant", Fuel.gasoline, 10000)
    storage.update(ad)
  }

  override def delete(id: Int): Int = {
    storage.delete(id)
  }
}