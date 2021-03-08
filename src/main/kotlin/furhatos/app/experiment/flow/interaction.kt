package furhatos.app.experiment.flow

import furhatos.nlu.common.*
import furhatos.flow.kotlin.*
import furhatos.app.experiment.nlu.*
import furhatos.gestures.Gestures
import furhatos.gestures.BasicParams
import furhatos.gestures.defineGesture
import furhatos.records.Location

val Start : State = state(Interaction) {

    onEntry {
        furhat.say("Hello! Nice to see you!" )
        furhat.gesture(Gestures.Smile(duration = 2.0, strength = 1.0))
        furhat.ask("Is it okay to talk now?")
    }

    onReentry {
        furhat.say(empathicRobot)
        furhat.say("I am ${furhat.voice.emphasis("really")} happy to be here")
        furhat.say("Now I will make a slight pause " +
                "${furhat.voice.pause("1000ms")} before continuing")
    }

    onResponse<Yes>{
        furhat.say("That's great!")
        goto(WeeklyIntroduction)
    }

    onResponse<No>{
        furhat.gesture(Gestures.Surprise(duration = 1.0, strength = 0.5))
        furhat.say("Oh! I am sorry that I am not helpful today")
        goto(WeeklyIntroduction)
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

val empathicRobot = utterance {
    +"Hi there,"
    +Gestures.Smile
    +"nice to meet you."
    +glance(Location.LEFT)
    +"I am so happy to interact with you again!"
    +Gestures.Wink
}

val WeeklyIntroduction = state(Interaction) {
    onEntry {
        random(
            //{ furhat.ask("How are you feeling today?") },
            { furhat.ask("How are you feeling today?")
                furhat.gesture(MyGesture)}
        )
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
}

val WeeklyCheckUp = state(Interaction) {
    onEntry {
        furhat.ask("Did you take your medication?")
    }

    onResponse<Yes> {
        furhat.gesture(Gestures.BigSmile)
        furhat.say("That is great!")
    }

    onResponse<No> {
        furhat.gesture(Gestures.BrowFrown)
        goto(NegativeMedicationResponse)
        //furhat.ask("Why is that?")
    }
}

val NegativeMedicationResponse = state(Interaction) {
    onEntry {
        furhat.ask("Why is that?")
    }

    onResponse<ForgetMedicationResponse> {
        furhat.gesture(Gestures.Oh)
        furhat.say("It's easy to forget. Good thing that I reminded you")
    }

    onResponse<NoTimeMedicationResponse> {
        furhat.gesture(Gestures.Oh)
        furhat.ask("All right! Just don’t forget to take them")
        furhat.gesture(Gestures.Wink)
    }

    onResponse<NoMedicationResponse> {
        furhat.gesture(Gestures.Oh)
        furhat.ask("I see. That’s good then!")
        furhat.gesture(Gestures.Wink)
    }
}
