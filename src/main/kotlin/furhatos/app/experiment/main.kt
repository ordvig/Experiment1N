package furhatos.app.experiment

import furhatos.app.experiment.flow.*
import furhatos.skills.Skill
import furhatos.flow.kotlin.*

class ExperimentSkill : Skill() {
    override fun start() {
        Flow().run(Idle)
    }
}

fun main(args: Array<String>) {
    Skill.main(args)
}
