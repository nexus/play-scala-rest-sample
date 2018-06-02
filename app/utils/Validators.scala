package utils

import java.time.LocalDate
import java.time.format.DateTimeFormatter

import models.Fuel
import play.api.data.Forms.nonEmptyText
import play.api.data.Mapping
import play.api.data.validation.{Constraint, Invalid, Valid, ValidationError}




object Validators {

  def sortBy: Mapping[String] = {
    nonEmptyText verifying Constraint[String]("constraint.fuel") { o: String =>
      val fields = Array("id", "title", "fuel", "price", "mileage", "firstReg", "isNew")
      if (!fields.contains(o))
        Invalid(ValidationError("Name of a field for 'sortBy' is wrong, valid values " + fields.mkString("['","', '","']")))
      else
        Valid
    }
  }

  def fuel: Mapping[String] = {
    nonEmptyText verifying Constraint[String]("constraint.fuel") { o: String =>
      if (Fuel.withNameOpt(o).isEmpty)
        Invalid(ValidationError("Fuel is wrong, valid values " + Fuel.values.mkString("['","', '","']")))
      else
        Valid
    }
  }

  def regDate: Mapping[String] = {
    nonEmptyText verifying Constraint[String]("constraint.regDate") { o: String =>
      if (!o.matches("\\d{4}-\\d{2}-\\d{2}"))
        Invalid(ValidationError("Date format should be yyyy-MM-dd"))
      else if (!dateAlreadyPassed(o))
        Invalid(ValidationError("Date is not already passed"))
      else
        Valid
    }
  }

  private def dateAlreadyPassed(date: String): Boolean = {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val parsedDate = LocalDate.parse(date, formatter)
    val today = LocalDate.now()
    today.isAfter(parsedDate)
  }

}
