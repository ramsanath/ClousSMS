package com.sanath.cloudsms.screens

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View


/**
 * Created by sanath on 26/05/18.
 */
class RecyclerItemClickListener : RecyclerView.OnItemTouchListener {

    private var mListener: OnItemClickListener? = null
    private var mGestureDetector: GestureDetector? = null

    constructor(context: Context, recyclerView: RecyclerView, listener: OnItemClickListener) {
        mListener = listener

        mGestureDetector = GestureDetector(context, object : GestureDetector.SimpleOnGestureListener() {
            override fun onSingleTapUp(e: MotionEvent): Boolean {
                return true
            }

            override fun onLongPress(e: MotionEvent) {
                val childView = recyclerView.findChildViewUnder(e.x, e.y)

                if (childView != null && mListener != null) {
                    mListener?.onItemLongClick(childView, recyclerView.getChildAdapterPosition(childView))
                }
            }
        })
    }

    override fun onInterceptTouchEvent(rv: RecyclerView?, e: MotionEvent?): Boolean {
        val childView = rv?.findChildViewUnder(e!!.x, e.y)

        if (childView != null && mListener != null && mGestureDetector!!.onTouchEvent(e)) {
            mListener?.onItemClick(childView, rv.getChildAdapterPosition(childView))
        }
        return false
    }

    override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {}

    override fun onTouchEvent(rv: RecyclerView?, e: MotionEvent?) {}

    interface OnItemClickListener {
        fun onItemClick(view: View, position: Int)
        fun onItemLongClick(view: View, position: Int)
    }
}