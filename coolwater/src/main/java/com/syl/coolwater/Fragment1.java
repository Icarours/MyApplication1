package com.syl.coolwater;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.syl.coolwater.fragment.BaseFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Bright on 2018/9/7.
 *
 * @Describe
 * @Called
 */
public class Fragment1 extends BaseFragment implements View.OnClickListener {
    @Bind(R.id.textView2)
    TextView mTextView2;
    @Bind(R.id.button)
    Button mButton;
    private Test1 mTest1;

    @Override
    protected void init() {

    }

    @Override
    protected void initData() {

    }

    @Override
    public View initView() {
        View rootView = View.inflate(getActivity(), R.layout.fragment1, null);
        ButterKnife.bind(this, rootView);
        Intent intent = getActivity().getIntent();
        mTest1 = (Test1) intent.getSerializableExtra("test1");
        mButton.setOnClickListener(this);
        return rootView;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);

        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getActivity(),Main3Activity.class);
        intent.putExtra("test1",mTest1);
        System.out.println("Fragment1-->Main3Activity");
        startActivityForResult(intent,100);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==100&&resultCode==1001){
            System.out.println("requestCode=="+requestCode+"--resultCode=="+resultCode);
        }
    }
}
