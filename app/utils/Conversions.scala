package utils

object Conversions {
  implicit class GetOrElseWrapper[T](opt: Option[T]) {
    def ??(defaultValue: T) = opt.getOrElse(defaultValue)
  }
}
