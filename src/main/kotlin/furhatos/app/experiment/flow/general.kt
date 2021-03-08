package furhatos.app.experiment.flow

import furhatos.flow.kotlin.*
import furhatos.util.*

val Idle: State = state {

    init {
        /* Initial Settings */
        furhat.setVoice(Language.ENGLISH_US, Gender.MALE)
        if (users.count > 0) {
            furhat.attend(users.random)
            goto(Start)
        }
    }

    onEntry {
        // Resets Furhat attention.
        // It runs every time the state is transitioned to
        furhat.attendNobody()
    }

    onUserEnter {
        // Attends the current user
        furhat.attend(it)
        goto(Start)
    }
}

val Interaction: State = state {

    onUserLeave(instant = true) {
        if (users.count > 0) {
            if (it == users.current) {
                furhat.attend(users.other)
                goto(Start)
            } else {
                furhat.glance(it)
            }
        } else {
            goto(Idle)
        }
    }

    onUserEnter(instant = true) {
        furhat.glance(it)
    }
}

