package furhatos.app.experiment.flow

import furhatos.nlu.common.*
import furhatos.flow.kotlin.*
import furhatos.app.experiment.nlu.*
import furhatos.gestures.Gestures
import furhatos.gestures.BasicParams
import furhatos.gestures.defineGesture
import furhatos.records.Location

var hasFamily = false

val empathicRobot = utterance {
    +"Hi there,"
    +Gestures.Smile
    +"nice to meet you again."
    +glance(Location.LEFT)
    +"I am so happy to interact with you again!"
    +Gestures.Wink
}

val Start : State = state(Interaction) {

    onEntry {
        furhat.say(empathicRobot)
        furhat.gesture(Gestures.Smile(duration = 2.0, strength = 1.0))
        furhat.ask("Is it okay to talk now?")
    }

    onReentry {
        furhat.gesture(Gestures.Smile(duration = 2.0, strength = 1.0))
        furhat.ask("Is it okay to talk now?")
    }

    onResponse<Yes>{
        furhat.say("That's great!")
        goto(WeeklyIntroduction)
    }

    onResponse<No>{
        furhat.gesture(Gestures.Surprise(duration = 1.0, strength = 0.5))
        furhat.say("Oh! I am sorry that I am not helpful now. See you later!")
        terminate()
    }

    onResponse<RequestRepeat>{
        furhat.gesture(Gestures.Smile)
        furhat.say("Sure! I'll repeat")
        reentry()
    }
}

val MyGesture = defineGesture("MyGesture") {
    frame(0.32, 0.72){
        BasicParams.SMILE_CLOSED to 0.8
    }
    frame(0.2, 0.72){
        BasicParams.BROW_UP_LEFT to 1.0
        BasicParams.BROW_UP_RIGHT to 0.5
    }
    frame(0.16, 0.72){
        BasicParams.BLINK_LEFT to 0.1
        BasicParams.BLINK_RIGHT to 0.1
    }
    reset(1.04)
}

// Conversation about how you feeling
val WeeklyIntroduction = state(Interaction) {
    onEntry {
        furhat.ask("How are you feeling today?")
        furhat.gesture(MyGesture)
    }

    onReentry {
        furhat.ask("How are you feeling today?")
        furhat.gesture(MyGesture)
    }

    onResponse<NegativeResponse> {
        furhat.say("I am sorry to hear that")
        furhat.gesture(Gestures.ExpressSad)
        furhat.say("I ${furhat.voice.emphasis("really")} hope your day gets better")
        furhat.gesture(MyGesture)
        goto(WeeklyCheckUp)
    }

    onResponse<PositiveResponse> {
        furhat.say("I am glad to hear that")
        furhat.gesture(MyGesture)
        goto(WeeklyCheckUp)
    }

    onResponse<NeutralResponse> {
        furhat.say("Alright!")
        furhat.gesture(Gestures.Smile(duration = 2.0, strength = 1.0))
        furhat.say("May your day gets even better")
        furhat.gesture(MyGesture)
        goto(WeeklyCheckUp)
    }

    onResponse<RequestRepeat>{
        furhat.gesture(Gestures.Smile)
        furhat.say("Sure! I'll repeat")
        reentry()
    }
}

// Conversation about Medication
val WeeklyCheckUp = state(Interaction) {
    onEntry {
        furhat.ask("Did you take your medication?")
    }

    onReentry {
        furhat.ask("Did you take your medication?")
    }

    onResponse<Yes> {
        furhat.gesture(Gestures.BigSmile)
        furhat.say("That is great!")
        furhat.gesture(MyGesture)
        goto(SleepWellConversation)
    }

    onResponse<No> {
        furhat.gesture(Gestures.BrowFrown)
        goto(NegativeMedicationResponse)
    }

    onResponse<RequestRepeat>{
        furhat.gesture(Gestures.Smile)
        furhat.say("Sure! I'll repeat")
        reentry()
    }
}

val NegativeMedicationResponse = state(Interaction) {
    onEntry {
        furhat.ask("Why is that?")
    }

    onReentry {
        furhat.ask("Why is that?")
    }

    onResponse<ForgetMedicationResponse> {
        furhat.gesture(Gestures.Oh)
        furhat.say("It's easy to forget. Good thing that I reminded you")
        goto(SleepWellConversation)
    }

    onResponse<NoTimeMedicationResponse> {
        furhat.gesture(Gestures.Oh)
        furhat.ask("All right! Just don’t forget to take them")
        furhat.gesture(Gestures.Wink)
        goto(SleepWellConversation)
    }

    onResponse<NoMedicationResponse> {
        furhat.gesture(Gestures.Oh)
        furhat.ask("I see. That’s good then!")
        furhat.gesture(MyGesture)
        goto(SleepWellConversation)
    }

    onResponse<RequestRepeat>{
        furhat.gesture(Gestures.Smile)
        furhat.say("Sure! I'll repeat")
        reentry()
    }
}

// Conversation about Sleep
val SleepWellConversation = state(Interaction) {
    onEntry {
        furhat.ask("Do you sleep well at night?")
    }

    onReentry {
        furhat.ask("Do you sleep well at night?")
    }

    onResponse<Yes>{
        furhat.say("I'm glad to hear that!")
        goto(TiredFeelingConversation)
    }

    onResponse<No>{
        furhat.say("I'm sorry to hear that!")
        goto(TiredFeelingConversation)
    }

    onResponse<RequestRepeat>{
        furhat.gesture(Gestures.Smile)
        furhat.say("Sure! I'll repeat")
        reentry()
    }
}

// Conversation about feeling tired
val TiredFeelingConversation = state(Interaction) {
    onEntry {
        furhat.ask("Do you feel tired during the day?")
    }

    onReentry {
        furhat.ask("Do you feel tired during the day?")
    }

    onResponse<Yes>{
        furhat.gesture(Gestures.BrowFrown)
        goto(PositiveTiredResponse)
    }

    onResponse<No>{
        furhat.say("Sounds good!")
        goto(LonelyFeelingConversation)
    }

    onResponse<RequestRepeat>{
        furhat.gesture(Gestures.Smile)
        furhat.say("Sure! I'll repeat")
        reentry()
    }
}

val PositiveTiredResponse = state(Interaction) {
    onEntry {
        furhat.ask("${furhat.voice.emphasis("Oh")} All right. Have you told the caretaker about it?")
    }

    onReentry {
        furhat.ask("${furhat.voice.emphasis("Oh")} All right. Have you told the caretaker about it?")
    }

    onResponse<Yes>{
        furhat.say("That’s good. I hope you’ll relax more")
        furhat.gesture(MyGesture)
        goto(LonelyFeelingConversation)
    }

    onResponse<No>{
        furhat.gesture(Gestures.Thoughtful)
        furhat.say("I see. I would suggest you take it up with your caretaker. Maybe you lack some minerals or vitamins.")
        furhat.gesture(MyGesture)
        goto(LonelyFeelingConversation)
    }

    onResponse<RequestRepeat>{
        furhat.gesture(Gestures.Smile)
        furhat.say("Sure! I'll repeat")
        reentry()
    }
}

// About feeling lonely
val LonelyFeelingConversation = state(Interaction) {
    onEntry {
        furhat.ask("Do you feel lonely?")
    }

    onReentry {
        furhat.ask("Do you feel lonely?")
    }

    onResponse<Yes>{
        furhat.gesture(Gestures.Oh)
        furhat.say("That’s understandable with the current situation.")
        goto(LonelyFeelingContinution)
    }

    onResponse<No>{
        furhat.gesture(Gestures.BigSmile)
        furhat.say("I’m very glad to hear that!")
        goto(LonelyFeelingContinution)
    }

    onResponse<RequestRepeat>{
        furhat.gesture(Gestures.Smile)
        furhat.say("Sure! I'll repeat")
        reentry()
    }
}

val LonelyFeelingContinution = state(Interaction) {
    onEntry {
        furhat.ask("Do you have any family or friends you can talk to?")
    }

    onReentry {
        furhat.ask("Do you have any family or friends you can talk to?")
    }

    onResponse<Yes>{
        furhat.say(furhat.voice.emphasis("That is good"))
        hasFamily = true
        furhat.gesture(MyGesture)
        goto(SpokenToFamily)
    }

    onResponse<No>{
        furhat.gesture(Gestures.ExpressSad)
        furhat.say("I’m sorry to hear that")
        hasFamily = false
        goto(HobbiesConversation)
    }

    onResponse<RequestRepeat>{
        furhat.gesture(Gestures.Smile)
        furhat.say("Sure! I'll repeat")
        reentry()
    }
}

// About speaking to Family
val SpokenToFamily = state(Interaction) {
    onEntry {
        furhat.ask("Have you spoken with your family or friends recently?")
    }

    onReentry {
        furhat.ask("Have you spoken with your family or friends recently?")
    }

    onResponse<Yes>{
        furhat.say(furhat.voice.emphasis("That is good"))
        furhat.gesture(MyGesture)
        goto(HowAboutsOfFamily)
    }

    onResponse<No>{
        furhat.gesture(Gestures.Smile)
        furhat.say("I see. Perhaps you could call them later")
        goto(HobbiesConversation)
    }

    onResponse<RequestRepeat>{
        furhat.gesture(Gestures.Smile)
        furhat.say("Sure! I'll repeat")
        reentry()
    }
}

// About howabouts of Family
val HowAboutsOfFamily = state(Interaction) {
    onEntry {
        furhat.ask("Is your family doing ok?")
    }

    onReentry {
        furhat.ask("Is your family doing ok?")
    }

    onResponse<Yes>{
        furhat.gesture(Gestures.BigSmile)
        furhat.say("I'm happy to hear that!")
        furhat.gesture(MyGesture)
        goto(HobbiesConversation)
    }

    onResponse<No>{
        furhat.gesture(Gestures.ExpressSad)
        furhat.say("I’m sad to hear that")
        goto(HobbiesConversation)
    }

    onResponse<RequestRepeat>{
        furhat.gesture(Gestures.Smile)
        furhat.say("Sure! I'll repeat")
        reentry()
    }
}

// About hobbies
val HobbiesConversation = state(Interaction) {
    onEntry {
        furhat.ask("What do you normally do during the day?")
    }
    onReentry {
        furhat.ask("What do you normally do during the day?")
    }

    onResponse<HobbiesResponse>{
        furhat.gesture(Gestures.Smile)
        furhat.say("Alright!")
        furhat.gesture(MyGesture)
        goto(OnTodayConversation)
    }

    onResponse<RequestRepeat>{
        furhat.gesture(Gestures.Smile)
        furhat.say("Sure! I'll repeat.")
        reentry()
    }
}

// About today
val OnTodayConversation = state(Interaction) {
    onEntry {
        furhat.ask("Have you done anything interesting today?")
    }
    onReentry {
        furhat.ask("Have you done anything interesting today?")
    }

    onResponse<Yes>{
        furhat.gesture(Gestures.BigSmile)
        furhat.say("It’s good that you’re doing interesting things!")
        furhat.gesture(Gestures.Smile)
        furhat.say("It was nice talking to you!")
        furhat.gesture(Gestures.Wink)
        terminate()
    }

    onResponse<No>{
        furhat.gesture(Gestures.Thoughtful)
        furhat.say("Perhaps you can find something interesting to do later")
        furhat.gesture(Gestures.Smile)
        furhat.say("It was nice talking to you!")
        furhat.gesture(Gestures.Wink)
        terminate()
    }

    onResponse<RequestRepeat>{
        furhat.gesture(Gestures.Smile)
        furhat.say("Sure! I'll repeat")
        reentry()
    }

}