package com.ange_antn.imc_calculator

import android.content.Intent
import android.icu.text.DecimalFormat
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.slider.RangeSlider

class MainActivity : AppCompatActivity() {

    private var isMaleSelected = true
    private var isFemaleSelected = false

    private var currentHeight = 120
    private var currentWeight = 60
    private var currentAge = 20

    private lateinit var viewMale: CardView
    private lateinit var viewFemale: CardView

    private lateinit var tvHeight: TextView
    private lateinit var rsHeight: RangeSlider

    private lateinit var tvWeight: TextView
    private lateinit var btnMinusWeight: FloatingActionButton
    private lateinit var btnPlusWeight: FloatingActionButton

    private lateinit var tvAge: TextView
    private lateinit var btnMinusAge: FloatingActionButton
    private lateinit var btnPlusAge: FloatingActionButton

    private lateinit var btnCalc: AppCompatButton

    companion object {
        const val IMC_RESULT = "IMC_RESULT"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())

            val scale = resources.displayMetrics.density
            val paddingInPx = (24 * scale + 0.5f).toInt()

            v.setPadding(
                paddingInPx,
                systemBars.top,
                paddingInPx,
                systemBars.bottom + paddingInPx
            )
            insets
        }

        initComponents()
        initListeners()
        initUi()
    }

    private fun calcImc(): Double {
        return currentWeight / ((currentHeight.toDouble() / 100) * (currentHeight.toDouble() / 100))
    }

    private fun initComponents() {
        viewMale = findViewById(R.id.view_male)
        viewFemale = findViewById(R.id.view_female)
        tvHeight = findViewById(R.id.tvHeight)
        rsHeight = findViewById(R.id.rsHeight)
        tvWeight = findViewById(R.id.tvWeight)
        btnMinusWeight = findViewById(R.id.btnMinusWeight)
        btnPlusWeight = findViewById(R.id.btnPlusWeight)
        tvAge = findViewById(R.id.tvAge)
        btnMinusAge = findViewById(R.id.btnMinusAge)
        btnPlusAge = findViewById(R.id.btnPlusAge)
        btnCalc = findViewById(R.id.btnCalc)
    }

    private fun initListeners() {
        viewMale.setOnClickListener {
            changeGender(true)
            setGenderColor()
        }
        viewFemale.setOnClickListener {
            changeGender(false)
            setGenderColor()
        }
        rsHeight.addOnChangeListener { _, value, _ ->
            val df = DecimalFormat("#.##")
            tvHeight.text = "${df.format(value)} cm"
            currentHeight = value.toInt()
        }
        btnPlusWeight.setOnClickListener {
            currentWeight += 1
            setWeight()
        }
        btnMinusWeight.setOnClickListener {
            if (currentWeight > 1) {
                currentWeight -= 1
                setWeight()
            }
        }
        btnPlusAge.setOnClickListener {
            currentAge += 1
            setAge()
        }
        btnMinusAge.setOnClickListener {
            if (currentAge > 1) {
                currentAge -= 1
                setAge()
            }
        }
        btnCalc.setOnClickListener {
            val intent = Intent(this, ResultActivity::class.java)
            intent.putExtra(IMC_RESULT, calcImc())
            startActivity(intent)
        }
    }

    private fun changeGender(isMaleSelectedNow: Boolean) {
        isMaleSelected = isMaleSelectedNow
        isFemaleSelected = !isMaleSelectedNow
    }

    private fun setGenderColor() {
        viewMale.setCardBackgroundColor(getBackgroundColor(isMaleSelected))
        viewFemale.setCardBackgroundColor(getBackgroundColor(isFemaleSelected))
    }

    private fun getBackgroundColor(isSelected: Boolean): Int {
        val color =
            if (isSelected) R.color.background_component_selected else R.color.background_component

        return ContextCompat.getColor(this, color)
    }

    private fun initUIHeightSection() {
        tvHeight.text = "$currentHeight cm"
        rsHeight.setCustomThumbDrawable(R.drawable.circle)
    }

    private fun setWeight() {
        tvWeight.text = currentWeight.toString()
    }

    private fun setAge() {
        tvAge.text = currentAge.toString()
    }

    private fun initUi() {
        setGenderColor()
        initUIHeightSection()
        setWeight()
        setAge()
    }
}