package com.example.storyapp.util

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.view.View

class AnimatorUtil {
    companion object {
        private const val ANIMATION_TIME = 200L
        private const val START_DELAY = 800L

        fun playSequentialAlphaAnimation(vararg views: View) {
            val animators = mutableListOf<Animator>()
            views.forEach { view ->
                animators.add(ObjectAnimator.ofFloat(view, View.ALPHA, 1f).setDuration(ANIMATION_TIME))
            }
            AnimatorSet().apply {
                playSequentially(animators)
                startDelay = START_DELAY
                start()
            }
        }
    }

}