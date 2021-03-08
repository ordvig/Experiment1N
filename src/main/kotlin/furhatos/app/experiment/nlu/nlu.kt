package furhatos.app.experiment.nlu

import furhatos.nlu.*
import furhatos.nlu.common.Number
import furhatos.nlu.EnumEntity
import furhatos.util.Language
import furhatos.nlu.Intent

class Room : EnumEntity(stemming = true, speechRecPhrases = true){
    override fun getEnum(lang: Language): List<String> {
        return listOf("single", "double", "superior", "superior with sea view")
    }
}

class Date : EnumEntity(stemming = true, speechRecPhrases = true){
    override fun getEnum(lang: Language): List<String> {
        return listOf("Monday", "next week", "weekend", "saturday")
    }
}

class ResquestOptions(
    val room : Room? = null,
    val date : Date? = null): Intent(){
    override fun getExamples(lang: Language): List<String> {
        return listOf("Do you have @room available for the @date ?",
            "Do you have the @room available?",
            "What kind of services do you provide?",
            "options", "give me the options", "@room")
    }
}

class RoomList: ListEntity<QuantifiedRoom>()

class QuantifiedRoom(
    // We need to update the BuyRoom intent to take in a RoomList
    val count : Number? = Number(1),
    val room: Room? = null) : ComplexEnumEntity () {
    override fun getEnum(lang: Language): List<String> {
        // Either several rooms or one room
        return listOf("@count @room", "@room")
    }
    override fun toText(): String {
        // The robot confirms the order by repeating the
        // quantity and the rooms
        return generate("$count $room")
    }
}

class BookRoom(val rooms: RoomList? = null): Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf("@rooms", "I want the single room",
            "I would like the room with the sea view",
            "I want to book the @rooms")
    }
}

class PositiveResponse(): Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf("I am good!",
            "I am happy!",
            "I am fine")
    }
}

class NegativeResponse(): Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf("I am not feeling good today",
            "Not really good",
            "I am having a bad day")
    }
}

class NeutralResponse(): Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf("I am okay")
    }
}
