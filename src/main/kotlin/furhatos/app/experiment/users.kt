package furhatos.app.experiment.flow

import furhatos.records.User  //Import User records
//import furhatos.app.experiment.nlu.RoomList

/* Storing a user's order on the user object
 * We define a Kotlin data class OrderData with a variable
 * 'rooms' of type RoomList

class OrderData (
    var rooms : RoomList = RoomList()
)

/* We add an extension variable 'order' to the User model of type OrderData*/
val User.order : OrderData
    get() = data.getOrPut(OrderData::class.qualifiedName, OrderData()) */

