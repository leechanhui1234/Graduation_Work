package com.example.aop.part4.graduation_work

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.CompoundButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.aop.part4.graduation_work.Board.ChatActivity
import com.example.aop.part4.graduation_work.databinding.HealthCheckBinding

class HealthCheck : AppCompatActivity() {

    private lateinit var binding : HealthCheckBinding
    private var thing_list : String = ""
    private var part_list : String = ""
    private var count : Int = 0

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = HealthCheckBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {

            var things_listener = CompoundButton.OnCheckedChangeListener { button, isChecked ->
                if (isChecked) {
                    when(button.id) {
                        R.id.thing1 -> {thing_list += thing1.text.toString() + ","}
                        R.id.thing2 -> {thing_list += thing1.text.toString() + ","}
                        R.id.thing3 -> {thing_list += thing1.text.toString() + ","}
                        R.id.thing4 -> {thing_list += thing1.text.toString() + ","}
                        R.id.thing5 -> {thing_list += thing1.text.toString() + ","}
                        R.id.thing6 -> {thing_list += thing1.text.toString() + ","}
                        R.id.thing7 -> {thing_list += thing1.text.toString() + ","}
                        R.id.thing8 -> {thing_list += thing1.text.toString() + ","}
                        R.id.thing9 -> {thing_list += thing1.text.toString() + ","}
                        R.id.thing10 -> {thing_list += thing1.text.toString() + ","}
                        R.id.thing11 -> {thing_list += thing1.text.toString() + ","}
                        R.id.thing12 -> {thing_list += thing1.text.toString() + ","}
                        R.id.thing13 -> {thing_list += thing1.text.toString() + ","}
                        R.id.thing14 -> {thing_list += thing1.text.toString() + ","}
                        R.id.thing15 -> {thing_list += thing1.text.toString() + ","}
                        R.id.thing16 -> {thing_list += thing1.text.toString() + ","}
                    }
                }
                else {
                    when(button.id) {
                        R.id.thing1 -> {thing_list = thing_list.replace(thing1.text.toString() + ",", "")}
                        R.id.thing2 -> {thing_list = thing_list.replace(thing2.text.toString() + ",", "")}
                        R.id.thing3 -> {thing_list = thing_list.replace(thing3.text.toString() + ",", "")}
                        R.id.thing4 -> {thing_list = thing_list.replace(thing4.text.toString() + ",", "")}
                        R.id.thing5 -> {thing_list = thing_list.replace(thing5.text.toString() + ",", "")}
                        R.id.thing6 -> {thing_list = thing_list.replace(thing6.text.toString() + ",", "")}
                        R.id.thing7 -> {thing_list = thing_list.replace(thing7.text.toString() + ",", "")}
                        R.id.thing8 -> {thing_list = thing_list.replace(thing8.text.toString() + ",", "")}
                        R.id.thing9 -> {thing_list = thing_list.replace(thing9.text.toString() + ",", "")}
                        R.id.thing10 -> {thing_list = thing_list.replace(thing10.text.toString() + ",", "")}
                        R.id.thing11 -> {thing_list = thing_list.replace(thing11.text.toString() + ",", "")}
                        R.id.thing12 -> {thing_list = thing_list.replace(thing12.text.toString() + ",", "")}
                        R.id.thing13 -> {thing_list = thing_list.replace(thing13.text.toString() + ",", "")}
                        R.id.thing14 -> {thing_list = thing_list.replace(thing14.text.toString() + ",", "")}
                        R.id.thing15 -> {thing_list = thing_list.replace(thing15.text.toString() + ",", "")}
                        R.id.thing16 -> {thing_list = thing_list.replace(thing16.text.toString() + ",", "")}
                    }
                }
            }

            var part_listener = CompoundButton.OnCheckedChangeListener { button, isChecked ->
                if (isChecked) {
                    when(button.id) {
                        R.id.part1 -> {part_list += part1.text.toString() + ","}
                        R.id.part2 -> {part_list += part2.text.toString() + ","}
                        R.id.part3 -> {part_list += part3.text.toString() + ","}
                        R.id.part4 -> {part_list += part4.text.toString() + ","}
                        R.id.part5 -> {part_list += part5.text.toString() + ","}
                        R.id.part6 -> {part_list += part6.text.toString() + ","}
                        R.id.part7 -> {part_list += part7.text.toString() + ","}
                        R.id.part8 -> {part_list += part8.text.toString() + ","}
                        R.id.part9 -> {part_list += part9.text.toString() + ","}
                        R.id.part10 -> {part_list += part10.text.toString() + ","}
                        R.id.part11 -> {part_list += part11.text.toString() + ","}
                        R.id.part12 -> {part_list += part12.text.toString() + ","}
                        R.id.part13 -> {part_list += part13.text.toString() + ","}
                        R.id.part14 -> {part_list += part14.text.toString() + ","}
                        R.id.part15 -> {part_list += part15.text.toString() + ","}
                        R.id.part16 -> {part_list += part16.text.toString() + ","}
                        R.id.part17 -> {part_list += part17.text.toString() + ","}
                        R.id.part18 -> {part_list += part18.text.toString() + ","}
                        R.id.part19 -> {part_list += part19.text.toString() + ","}
                        R.id.part20 -> {part_list += part20.text.toString() + ","}
                    }
                    count += 1
                }
                else {
                    when(button.id) {
                        R.id.part1 -> {part_list = part_list.replace(part1.text.toString() + ",", "")}
                        R.id.part2 -> {part_list = part_list.replace(part2.text.toString() + ",", "")}
                        R.id.part3 -> {part_list = part_list.replace(part3.text.toString() + ",", "")}
                        R.id.part4 -> {part_list = part_list.replace(part4.text.toString() + ",", "")}
                        R.id.part5 -> {part_list = part_list.replace(part5.text.toString() + ",", "")}
                        R.id.part6 -> {part_list = part_list.replace(part6.text.toString() + ",", "")}
                        R.id.part7 -> {part_list = part_list.replace(part7.text.toString() + ",", "")}
                        R.id.part8 -> {part_list = part_list.replace(part8.text.toString() + ",", "")}
                        R.id.part9 -> {part_list = part_list.replace(part9.text.toString() + ",", "")}
                        R.id.part10 -> {part_list = part_list.replace(part10.text.toString() + ",", "")}
                        R.id.part11 -> {part_list = part_list.replace(part11.text.toString() + ",", "")}
                        R.id.part12 -> {part_list = part_list.replace(part12.text.toString() + ",", "")}
                        R.id.part13 -> {part_list = part_list.replace(part13.text.toString() + ",", "")}
                        R.id.part14 -> {part_list = part_list.replace(part14.text.toString() + ",", "")}
                        R.id.part15 -> {part_list = part_list.replace(part15.text.toString() + ",", "")}
                        R.id.part16 -> {part_list = part_list.replace(part16.text.toString() + ",", "")}
                        R.id.part17 -> {part_list = part_list.replace(part17.text.toString() + ",", "")}
                        R.id.part18 -> {part_list = part_list.replace(part18.text.toString() + ",", "")}
                        R.id.part19 -> {part_list = part_list.replace(part19.text.toString() + ",", "")}
                        R.id.part20 -> {part_list = part_list.replace(part20.text.toString() + ",", "")}
                    }
                    count -= 1
                }
            }

            thing1.setOnCheckedChangeListener(things_listener)
            thing2.setOnCheckedChangeListener(things_listener)
            thing3.setOnCheckedChangeListener(things_listener)
            thing4.setOnCheckedChangeListener(things_listener)
            thing5.setOnCheckedChangeListener(things_listener)
            thing6.setOnCheckedChangeListener(things_listener)
            thing7.setOnCheckedChangeListener(things_listener)
            thing8.setOnCheckedChangeListener(things_listener)
            thing9.setOnCheckedChangeListener(things_listener)
            thing10.setOnCheckedChangeListener(things_listener)
            thing11.setOnCheckedChangeListener(things_listener)
            thing12.setOnCheckedChangeListener(things_listener)
            thing13.setOnCheckedChangeListener(things_listener)
            thing14.setOnCheckedChangeListener(things_listener)
            thing15.setOnCheckedChangeListener(things_listener)
            thing16.setOnCheckedChangeListener(things_listener)

            part1.setOnCheckedChangeListener(part_listener)
            part2.setOnCheckedChangeListener(part_listener)
            part3.setOnCheckedChangeListener(part_listener)
            part4.setOnCheckedChangeListener(part_listener)
            part5.setOnCheckedChangeListener(part_listener)
            part6.setOnCheckedChangeListener(part_listener)
            part7.setOnCheckedChangeListener(part_listener)
            part8.setOnCheckedChangeListener(part_listener)
            part9.setOnCheckedChangeListener(part_listener)
            part10.setOnCheckedChangeListener(part_listener)
            part11.setOnCheckedChangeListener(part_listener)
            part12.setOnCheckedChangeListener(part_listener)
            part13.setOnCheckedChangeListener(part_listener)
            part14.setOnCheckedChangeListener(part_listener)
            part15.setOnCheckedChangeListener(part_listener)
            part16.setOnCheckedChangeListener(part_listener)
            part17.setOnCheckedChangeListener(part_listener)
            part18.setOnCheckedChangeListener(part_listener)
            part19.setOnCheckedChangeListener(part_listener)
            part20.setOnCheckedChangeListener(part_listener)


            reset.setOnClickListener {
                thing1.isChecked = false
                thing2.isChecked = false
                thing3.isChecked = false
                thing4.isChecked = false
                thing5.isChecked = false
                thing6.isChecked = false
                thing7.isChecked = false
                thing8.isChecked = false
                thing9.isChecked = false
                thing10.isChecked = false
                thing11.isChecked = false
                thing12.isChecked = false
                thing13.isChecked = false
                thing14.isChecked = false
                thing15.isChecked = false
                thing16.isChecked = false

                part1.isChecked = false
                part2.isChecked = false
                part3.isChecked = false
                part4.isChecked = false
                part5.isChecked = false
                part6.isChecked = false
                part7.isChecked = false
                part8.isChecked = false
                part9.isChecked = false
                part10.isChecked = false
                part11.isChecked = false
                part12.isChecked = false
                part13.isChecked = false
                part14.isChecked = false
                part15.isChecked = false
                part16.isChecked = false
                part17.isChecked = false
                part18.isChecked = false
                part19.isChecked = false
                part20.isChecked = false
            }

            healthSave.setOnClickListener {
                if (count < 3) {
                    Toast.makeText(applicationContext, "운동 부위는 3개 이상 골라주세요.", Toast.LENGTH_SHORT).show()
                }
                else {
                    Toast.makeText(applicationContext, "${thing_list}, ${part_list}", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@HealthCheck, Health::class.java)
                    intent.putExtra("things", thing_list)
                    intent.putExtra("parts", part_list)
                    startActivity(intent)
                }
            }

        }
    }
}