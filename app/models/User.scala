package models

import anorm.Pk
import anorm.SQL
import anorm._
import anorm.SqlParser._
import play.api.db._
import play.api.Play.current

sealed abstract class UserCommon {
  val username: String
  val firstname: String
  val lastname: String
}
case class JsonUser(username: String, firstname: String, lastname: String)
  extends UserCommon
case class User(id: Long, username: String, firstname: String, lastname: String)
  extends UserCommon

object Users {
  val simple = {
    get[Long]("id") ~
      str("username") ~
      str("firstname") ~
      str("lastname") map {
        case id ~ u ~ f ~ l => User(id, u, f, l)
      }
  }

  def findAll(): Seq[User] = {
    DB.withConnection { implicit c =>
      SQL("select * from user").as(Users.simple *)
    }
  }

  def find(id: Long) = {
    DB.withConnection { implicit c =>
      SQL("select * from user where id = {id}").on('id -> id).as(Users.simple.singleOpt)
    }
  }

  def create(u: UserCommon): Option[User] = {
    DB.withConnection { implicit c =>
      SQL("insert into user(username, firstname, lastname) values ({name},{firstname},{lastname})").on(
        'name -> u.username,
        'firstname -> u.firstname,
        'lastname -> u.lastname).executeInsert()
    } flatMap { l => find(l) }
  }

  def delete(id: Long): Int = {
    DB.withConnection { implicit c =>
      SQL("DELETE from user where id = {id}").on('id -> id).executeUpdate()
    }
  }

  def update(id: Long, newUser: UserCommon) = {
    DB.withConnection { implicit c =>
      SQL("""UPDATE user
          set username = {username}, firstname = {firstname}, lastname = {lastname}
          where id = {id}""")
        .on('id -> id,
          'username -> newUser.username,
          'firstname -> newUser.firstname,
          'lastname -> newUser.lastname).executeUpdate()
    }
    find(id)
  }
}