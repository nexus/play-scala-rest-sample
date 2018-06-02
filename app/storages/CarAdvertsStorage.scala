package storages

import models.CarAdvert

trait CarAdvertsStorage {
  def list(sortBy: String): Seq[CarAdvert]
  def get(id: Int): Option[CarAdvert]
  def create(ad: CarAdvert): CarAdvert
  def update(ad: CarAdvert): Int
  def delete(id: Int): Int
}

//val newAd = CarAdvertNew(Some(1), "AUDI A4 avant", Fuel.Gasoline, 10000)
//
//val oldCarReg = LocalDate.parse("2017-01-01")
//val oldAd = CarAdvertOld(Some(2), "AUDI A6 avant", Fuel.Gasoline, 10000, 5320, oldCarReg)
//
//val ads = List[CarAdvert](newAd, oldAd)
