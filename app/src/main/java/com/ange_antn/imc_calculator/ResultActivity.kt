package com.ange_antn.imc_calculator

import android.icu.text.DecimalFormat
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.ange_antn.imc_calculator.MainActivity.Companion.IMC_RESULT

class ResultActivity : AppCompatActivity() {

    private lateinit var tvResult: TextView
    private lateinit var tvImc: TextView
    private lateinit var tvDescription: TextView
    private lateinit var btnRecalc: AppCompatButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_result)
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

        val result: Double = intent.extras?.getDouble(IMC_RESULT) ?: -1.0
        initComponents()
        initUi(result)
        initListeners()
    }

    private fun initListeners() {
        btnRecalc.setOnClickListener { onBackPressedDispatcher.onBackPressed() }
    }

    private fun initUi(result: Double) {
        val df = DecimalFormat("#.##")

        tvImc.text = df.format(result).toString()

        when (result) {
            -1.0 -> {
                tvResult.text = getString(R.string.error_result_label)
                tvDescription.text = getString(R.string.error_description_label)
            }

            in 0.0..18.5 -> {
                tvResult.text = getString(R.string.low_result_label)
                tvResult.setTextColor(ContextCompat.getColor(this, R.color.yellow))
                tvDescription.text = getString(R.string.low_description_label)
            }

            in 18.51..24.99 -> {
                tvResult.text = getString(R.string.normal_result_label)
                tvResult.setTextColor(ContextCompat.getColor(this, R.color.green))
                tvDescription.text = getString(R.string.normal_description_label)
            }

            in 25.0..29.99 -> {
                tvResult.text = getString(R.string.high_result_label)
                tvResult.setTextColor(ContextCompat.getColor(this, R.color.orange))
                tvDescription.text = getString(R.string.high_description_label)
            }

            else -> {
                tvResult.text = getString(R.string.obesity_result_label)
                tvResult.setTextColor(ContextCompat.getColor(this, R.color.red))
                tvDescription.text = getString(R.string.obesity_description_label)
            }
        }
    }

    private fun initComponents() {
        tvResult = findViewById(R.id.tvResult)
        tvImc = findViewById(R.id.tvImc)
        tvDescription = findViewById(R.id.tvDescription)
        btnRecalc = findViewById(R.id.btnRecalc)
    }
}