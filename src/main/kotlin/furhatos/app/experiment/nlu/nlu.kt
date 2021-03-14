package furhatos.app.experiment.nlu

import furhatos.nlu.*
import furhatos.nlu.common.Number
import furhatos.nlu.EnumEntity
import furhatos.util.Language
import furhatos.nlu.Intent

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

class HobbiesResponse(): Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf("I like to watch TV",
        "I like to talk to friends",
        "I like gardening")
    }
}

class ForgetMedicationResponse(): Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf("I forgot them", "I forgot to have them", "Forgot")
    }
}

class NoTimeMedicationResponse(): Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf("No time to take my pills"
            , "Too busy to take my medicines"
            , "No time to have my medicines"
            , "Couldn't find time to take medicines")
    }
}

class NoMedicationResponse(): Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf("Nothing to take today")
    }
}