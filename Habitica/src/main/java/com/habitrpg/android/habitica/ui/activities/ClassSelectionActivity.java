package com.habitrpg.android.habitica.ui.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.habitrpg.android.habitica.R;
import com.habitrpg.android.habitica.userpicture.UserPicture;
import com.magicmicky.habitrpgwrapper.lib.models.Gear;
import com.magicmicky.habitrpgwrapper.lib.models.HabitRPGUser;
import com.magicmicky.habitrpgwrapper.lib.models.Hair;
import com.magicmicky.habitrpgwrapper.lib.models.Items;
import com.magicmicky.habitrpgwrapper.lib.models.Outfit;
import com.magicmicky.habitrpgwrapper.lib.models.Preferences;

import butterknife.Bind;
import butterknife.OnClick;

public class ClassSelectionActivity extends BaseActivity {

    Boolean isInitialSelection;

    @Bind(R.id.healerImageView)
    ImageView healerImageView;
    @Bind(R.id.mageImageView)
    ImageView mageImageView;
    @Bind(R.id.rogueImageView)
    ImageView rogueImageView;
    @Bind(R.id.warriorImageView)
    ImageView warriorImageView;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_class_selection;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        isInitialSelection = bundle.getBoolean("isInitialSelection");

        Preferences preferences = new Preferences();
        preferences.setHair(new Hair());
        preferences.setCostume(false);
        preferences.setSize(bundle.getString("size"));
        preferences.setSkin(bundle.getString("skin"));
        preferences.setShirt(bundle.getString("shirt"));
        preferences.getHair().setBangs(bundle.getInt("hairBangs"));
        preferences.getHair().setBase(bundle.getInt("hairBase"));
        preferences.getHair().setColor(bundle.getString("hairColor"));
        preferences.getHair().setMustache(bundle.getInt("hairMustache"));
        preferences.getHair().setBeard(bundle.getInt("hairBeard"));


        Outfit healerOutfit = new Outfit();
        healerOutfit.setArmor("armor_healer_5");
        healerOutfit.setHead("head_healer_5");
        healerOutfit.setShield("shield_healer_5");
        healerOutfit.setWeapon("weapon_healer_6");
        HabitRPGUser healer = this.makeUser(preferences, healerOutfit);
        UserPicture healerUserPicture = new UserPicture(this);
        healerUserPicture.setUser(healer);
        healerUserPicture.setPictureOn(healerImageView);

        Outfit mageOutfit = new Outfit();
        mageOutfit.setArmor("armor_wizard_5");
        mageOutfit.setHead("head_wizard_5");
        mageOutfit.setWeapon("weapon_wizard_6");
        HabitRPGUser mage = this.makeUser(preferences, mageOutfit);
        UserPicture mageUserPicture = new UserPicture(this);
        mageUserPicture.setUser(mage);
        mageUserPicture.setPictureOn(mageImageView);

        Outfit rogueOutfit = new Outfit();
        rogueOutfit.setArmor("armor_rogue_5");
        rogueOutfit.setHead("head_rogue_5");
        rogueOutfit.setShield("shield_rogue_6");
        rogueOutfit.setWeapon("weapon_rogue_6");
        HabitRPGUser rogue = this.makeUser(preferences, rogueOutfit);
        UserPicture rogueUserPicture = new UserPicture(this);
        rogueUserPicture.setUser(rogue);
        rogueUserPicture.setPictureOn(rogueImageView);

        Outfit warriorOutfit = new Outfit();
        warriorOutfit.setArmor("armor_warrior_5");
        warriorOutfit.setHead("head_warrior_5");
        warriorOutfit.setShield("shield_warrior_5");
        warriorOutfit.setWeapon("weapon_warrior_6");
        HabitRPGUser warrior = this.makeUser(preferences, warriorOutfit);
        UserPicture warriorUserPicture = new UserPicture(this);
        warriorUserPicture.setUser(warrior);
        warriorUserPicture.setPictureOn(warriorImageView);
    }

    public HabitRPGUser makeUser(Preferences preferences, Outfit outfit) {
        HabitRPGUser user = new HabitRPGUser();
        user.setPreferences(preferences);
        user.setItems(new Items());
        user.getItems().setGear(new Gear());
        user.getItems().getGear().setEquipped(outfit);
        return user;
    }

    @OnClick(R.id.healerWrapper)
    public void healerSelected() {
        displayConfirmationDialogForClass(getString(R.string.healer), "healer");
    }

    @OnClick(R.id.mageWrapper)
    public void mageSelected() {
        displayConfirmationDialogForClass(getString(R.string.mage), "wizard");
    }

    @OnClick(R.id.rogueWrapper)
    public void rogueSelected() {
        displayConfirmationDialogForClass(getString(R.string.rogue), "rogue");
    }

    @OnClick(R.id.warriorWrapper)
    public void warriorSelected() {
        displayConfirmationDialogForClass(getString(R.string.warrior), "warrior");
    }

    @OnClick(R.id.optOutWrapper)
    public void optOutSelected() {
        AlertDialog alert = new AlertDialog.Builder(this)
                .setTitle(getString(R.string.opt_out_confirmation))
                .setNegativeButton(getString(R.string.dialog_go_back), (dialog, which) -> {
                    dialog.dismiss();
                })
                .setPositiveButton(getString(R.string.opt_out_class), (dialog, which) -> {
                    Intent intent = new Intent();
                    intent.putExtra("optedOut", true);
                    setResult(RESULT_OK, intent);
                    finish();
                }).create();
        alert.show();
    }

    private void displayConfirmationDialogForClass(String className, String classIdentifier) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle(getString(R.string.class_confirmation, className))
                .setNegativeButton(getString(R.string.dialog_go_back), (dialog, which) -> {
                    dialog.dismiss();
                })
                .setPositiveButton(getString(R.string.choose_class), (dialog, which) -> {

                    Intent intent = new Intent();
                    intent.putExtra("selectedClass", classIdentifier);
                    setResult(RESULT_OK, intent);
                    finish();
                });
        AlertDialog alert = builder.create();
        alert.show();
    }
}
