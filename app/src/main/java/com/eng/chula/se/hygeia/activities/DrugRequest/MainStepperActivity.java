package com.eng.chula.se.hygeia.activities.DrugRequest;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.eng.chula.se.hygeia.R;

import java.util.ArrayList;

import me.drozdzynski.library.steppers.SteppersItem;
import me.drozdzynski.library.steppers.SteppersView;
import me.drozdzynski.library.steppers.interfaces.OnCancelAction;
import me.drozdzynski.library.steppers.interfaces.OnChangeStepAction;
import me.drozdzynski.library.steppers.interfaces.OnClickContinue;
import me.drozdzynski.library.steppers.interfaces.OnFinishAction;
import me.drozdzynski.library.steppers.interfaces.OnSkipStepAction;

public class MainStepperActivity extends AppCompatActivity {

    private SteppersView steppersView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_stepper);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        steppersView = (SteppersView) findViewById(R.id.steppersView);
        SteppersView.Config steppersViewConfig = new SteppersView.Config();
        steppersViewConfig.setOnFinishAction(new OnFinishAction() {
            @Override
            public void onFinish() {
                MainStepperActivity.this.startActivity(new Intent(MainStepperActivity.this, MainStepperActivity.class));
                MainStepperActivity.this.finish();
            }
        });

        steppersViewConfig.setOnCancelAction(new OnCancelAction() {
            @Override
            public void onCancel() {
                MainStepperActivity.this.startActivity(new Intent(MainStepperActivity.this, MainStepperActivity.class));
                MainStepperActivity.this.finish();
            }
        });

        steppersViewConfig.setOnChangeStepAction(new OnChangeStepAction() {
            @Override
            public void onChangeStep(int position, SteppersItem activeStep) {
                Toast.makeText(MainStepperActivity.this,
                        "Step changed to: " + activeStep.getLabel() + " (" + position + ")",
                        Toast.LENGTH_SHORT).show();
            }
        });

        steppersViewConfig.setFragmentManager(getSupportFragmentManager());
        ArrayList<SteppersItem> steps = new ArrayList<>();

        int i = 0;
        while (i <= 10) {

            final SteppersItem item = new SteppersItem();
            item.setLabel("Step nr " + i);
            item.setPositiveButtonEnable(i % 2 != 0);

            if(i % 2 == 0) {
                BlankFragment blankFragment = new BlankFragment();
                blankFragment.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        item.setPositiveButtonEnable(true);
                    }
                });
                if(i % 4 == 0) {
                    item.setSkippable(true, new OnSkipStepAction() {
                        @Override
                        public void onSkipStep() {
                            Toast skipToast = Toast.makeText(MainStepperActivity.this,
                                    "Step \"" + item.getLabel() + "\" skipped",
                                    Toast.LENGTH_SHORT);
                            skipToast.setGravity(Gravity.BOTTOM, 0, 50);
                            skipToast.show();
                        }
                    });
                } else {
                    item.setSkippable(true);
                }

                item.setOnClickContinue(new OnClickContinue() {
                    @Override
                    public void onClick() {
                        new AlertDialog.Builder(MainStepperActivity.this)
                                .setTitle("Are you really want continue?")
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        steppersView.nextStep();
                                    }
                                })
                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                    }
                                }).show();
                    }
                });

                item.setSubLabel("Fragment: " + blankFragment.getClass().getSimpleName());
                item.setFragment(blankFragment);
            } else {
                BlankSecondFragment blankSecondFragment = new BlankSecondFragment();
                item.setSubLabel("Fragment: " + blankSecondFragment.getClass().getSimpleName());
                item.setFragment(blankSecondFragment);
            }

            steps.add(item);
            i++;
        }

        steppersView.setConfig(steppersViewConfig);
        steppersView.setItems(steps);
        steppersView.build();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(steppersView != null) {
            switch (id) {
                case R.id.action_step_3:
                    steppersView.setActiveItem(3);
                    break;
                case R.id.action_step_5:
                    steppersView.setActiveItem(5);
                    break;
            }
        }

        return super.onOptionsItemSelected(item);
    }
}
