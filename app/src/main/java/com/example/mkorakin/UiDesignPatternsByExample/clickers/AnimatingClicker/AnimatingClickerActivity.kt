package com.example.mkorakin.UiDesignPatternsByExample.clickers.AnimatingClicker

import android.animation.Animator
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.mkorakin.UiDesignPatternsByExample.R
import com.example.mkorakin.UiDesignPatternsByExample.databinding.SimpleClickerMvpBinding

class AnimatingClickerActivity : AppCompatActivity(), Animator.AnimatorListener {

    var animating = false

    private lateinit var presenter: AnimatingClickerPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<SimpleClickerMvpBinding>(this, R.layout.simple_clicker_mvp)

        let { listener ->
            presenter = AnimatingClickerPresenter(object : AnimatingClickerView {
                override val isAnimating: Boolean
                    get() = animating

                override fun animate() {
                    binding.simpleClickerButton.animate().rotationBy(360f).setListener(listener)
                }
                
                override fun displayCount(count: Int) {
                    binding.simpleClickerButton.text = count.toString()
                }
            })
        }

        binding.controller = presenter
    }

    override fun onDestroy() {
        presenter.dispose()
        super.onDestroy()
    }

    override fun onAnimationRepeat(animation: Animator?) {
        animating = true
    }

    override fun onAnimationEnd(animation: Animator?) {
        animating = false
    }

    override fun onAnimationCancel(animation: Animator?) {
        animating = false
    }

    override fun onAnimationStart(animation: Animator?) {
        animating = true
    }
}