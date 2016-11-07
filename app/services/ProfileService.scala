package services

import com.google.inject.Inject
import entity.scala.{Profile, User}
import repository.ProfileRepository

import scala.concurrent.duration.{Duration, DurationInt}
import scala.concurrent.{Await, ExecutionContext, Future}
import scala.reflect.macros.whitebox

/**
  * Created by serhii.hokhkalenko on 2016-11-07.
  */
class ProfileService @Inject()(profileRepository: ProfileRepository,userService: UserService)(implicit exec: ExecutionContext) {

  /**
    * This method gets user profile by user
    *
    * @param userId
    * @return
    */
  def getUserProfileByUserId(userId: Long):Future[Option[Profile]]={
   val user= Await.result( userService.getById(userId),10.seconds)
       profileRepository.getById(user.get.profileId)
  }

  /**
    * This method gets user profile by user
    *
    * @param user
    * @return
    */
  def getUserProfile(user: User):Future[Option[Profile]]={
    profileRepository.getById(user.profileId)
 }
  /**
    * This method returns user by id if it exist
    *
    * @param id
    * @return
    */
  def getById(id:Long):Future[Option[Profile]]={
    profileRepository.getById(id)
  }

  /**
    * This method add new profile
    *
    * @param profile
    */
  def addProfile(profile:Profile){
    profileRepository.save(profile)
  }

  /**
    * This method updating existing profile
    *
    * @param profile
    */
  def updateProfile(profile: Profile){
    profileRepository.updateById(profile.id,profile)
  }

  /**
    * This method deleting profile
    *
    * @param id
    */
  def deleteProfile(id:Long){
    profileRepository.deleteById(id)
  }

  def getAllProfiles:Future[Seq[Profile]]={
    profileRepository.getAll
  }
}
