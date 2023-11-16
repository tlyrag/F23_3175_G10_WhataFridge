package com.douglas.whatafridge.Views;


import androidx.recyclerview.widget.GridLayoutManager;

import android.content.SharedPreferences;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.douglas.whatafridge.Controller.Adapters.VegeTypeRecyclerViewAdapter;
import com.douglas.whatafridge.Controller.Database.MyDBHelper;
import com.douglas.whatafridge.Controller.Database.UserDBController;
import com.douglas.whatafridge.Model.ObjectModels.User;
import com.douglas.whatafridge.Model.ObjectModels.VegeTypeImage;
import com.douglas.whatafridge.R;
import com.douglas.whatafridge.databinding.ActivityProfileBinding;

import java.util.ArrayList;
import java.util.List;


public class ProfileActivity extends WFTemplate implements VegeTypeRecyclerViewAdapter.OnItemClickListener{

    MyDBHelper myDBHelper;
    UserDBController db;
    User user;
    ActivityProfileBinding binding;

    List<VegeTypeImage> ImageList = new ArrayList<>();
    List<VegeTypeImage> oppImageList = new ArrayList<>();

    VegeTypeImage ok_dairy = new VegeTypeImage(101,"OK Dairy", R.drawable.ok_dairy);
    VegeTypeImage ok_egg = new VegeTypeImage(101,"OK Egg", R.drawable.ok_egg);
    VegeTypeImage ok_poultry = new VegeTypeImage(101,"OK Poultry", R.drawable.ok_poultry);
    VegeTypeImage ok_fish = new VegeTypeImage(101,"OK Fish", R.drawable.ok_fish);
    VegeTypeImage ok_pork = new VegeTypeImage(101,"OK Pork", R.drawable.ok_pork);
    VegeTypeImage ok_beef = new VegeTypeImage(101,"OK Beef", R.drawable.ok_beef);

    VegeTypeImage no_dairy = new VegeTypeImage(101,"No Dairy", R.drawable.no_dairy);
    VegeTypeImage no_egg = new VegeTypeImage(101,"No Egg", R.drawable.no_egg);
    VegeTypeImage no_poultry = new VegeTypeImage(101,"No Poultry", R.drawable.no_poultry);
    VegeTypeImage no_fish = new VegeTypeImage(101,"No Fish", R.drawable.no_fish);
    VegeTypeImage no_pork = new VegeTypeImage(101,"No Pork", R.drawable.no_pork);
    VegeTypeImage no_beef = new VegeTypeImage(101,"No Beef", R.drawable.no_beef);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getUserInfoDatabase();
        addData();

        binding.editTextProfileUserId.setEnabled(false);

        VegeTypeRecyclerViewAdapter myAdapter = new VegeTypeRecyclerViewAdapter(ImageList, oppImageList, this);
        GridLayoutManager gm = new GridLayoutManager(this, 3);
        binding.recyclerViewVegeType.setAdapter(myAdapter);
        binding.recyclerViewVegeType.setLayoutManager(gm);

        binding.spinnerTypeVegetarian.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String[] arr = getResources().getStringArray(R.array.typesOfVegetarians);
                if(!arr[position].equals(arr[user.getVegeType()])) {
                    Toast.makeText(ProfileActivity.this, arr[position], Toast.LENGTH_SHORT).show();
                }

                if(arr[position].equals("No Vegetarian")){
                    ImageList.clear();
                    oppImageList.clear();
                    addData();
                    myAdapter.setAdapterImages(ImageList);
                    myAdapter.setOppAdapterImages(oppImageList);
                }else if(arr[position].equals("Custom") && arr[user.getVegeType()].equals("Custom")){
                    ImageList.clear();
                    oppImageList.clear();
                    if(user.getDairy().equals("YES")){
                        ImageList.add(ok_dairy);
                        oppImageList.add(no_dairy);
                    }else{
                        ImageList.add(no_dairy);
                        oppImageList.add(ok_dairy);
                    }

                    if(user.getEgg().equals("YES")){
                        ImageList.add(ok_egg);
                        oppImageList.add(no_egg);
                    }else{
                        ImageList.add(no_egg);
                        oppImageList.add(ok_egg);
                    }

                    if(user.getPoultry().equals("YES")){
                        ImageList.add(ok_poultry);
                        oppImageList.add(no_poultry);
                    }else{
                        ImageList.add(no_poultry);
                        oppImageList.add(ok_poultry);
                    }

                    if(user.getFish().equals("YES")){
                        ImageList.add(ok_fish);
                        oppImageList.add(no_fish);
                    }else{
                        ImageList.add(no_fish);
                        oppImageList.add(ok_fish);
                    }

                    if(user.getPork().equals("YES")){
                        ImageList.add(ok_pork);
                        oppImageList.add(no_pork);
                    }else{
                        ImageList.add(no_pork);
                        oppImageList.add(ok_pork);
                    }

                    if(user.getBeef().equals("YES")){
                        ImageList.add(ok_beef);
                        oppImageList.add(no_beef);
                    }else{
                        ImageList.add(no_beef);
                        oppImageList.add(ok_beef);
                    }
                    myAdapter.setAdapterImages(ImageList);
                    myAdapter.setOppAdapterImages(oppImageList);
                }else if(arr[position].equals("Lacto-ovo vegetarian")){
                    // consume eggs and dairy products
                    ImageList.clear();
                    oppImageList.clear();

                    ImageList.add(ok_dairy);
                    ImageList.add(ok_egg);
                    ImageList.add(no_poultry);
                    ImageList.add(no_fish);
                    ImageList.add(no_pork);
                    ImageList.add(no_beef);

                    oppImageList.add(no_dairy);
                    oppImageList.add(no_egg);
                    oppImageList.add(ok_poultry);
                    oppImageList.add(ok_fish);
                    oppImageList.add(ok_pork);
                    oppImageList.add(ok_beef);

                    myAdapter.setAdapterImages(ImageList);
                    myAdapter.setOppAdapterImages(oppImageList);

                }else if(arr[position].equals("Lacto-vegetarian")){
                    //consume dairy products.
                    ImageList.clear();
                    oppImageList.clear();
                    ImageList.add(ok_dairy);
                    ImageList.add(no_egg);
                    ImageList.add(no_poultry);
                    ImageList.add(no_fish);
                    ImageList.add(no_pork);
                    ImageList.add(no_beef);

                    oppImageList.add(no_dairy);
                    oppImageList.add(ok_egg);
                    oppImageList.add(ok_poultry);
                    oppImageList.add(ok_fish);
                    oppImageList.add(ok_pork);
                    oppImageList.add(ok_beef);
                    myAdapter.setAdapterImages(ImageList);
                    myAdapter.setOppAdapterImages(oppImageList);
                }else if(arr[position].equals("Ovo-vegetarian")){
                    // consume eggs.
                    ImageList.clear();
                    oppImageList.clear();
                    ImageList.add(no_dairy);
                    ImageList.add(ok_egg);
                    ImageList.add(no_poultry);
                    ImageList.add(no_fish);
                    ImageList.add(no_pork);
                    ImageList.add(no_beef);

                    oppImageList.add(ok_dairy);
                    oppImageList.add(no_egg);
                    oppImageList.add(ok_poultry);
                    oppImageList.add(ok_fish);
                    oppImageList.add(ok_pork);
                    oppImageList.add(ok_beef);
                    myAdapter.setAdapterImages(ImageList);
                    myAdapter.setOppAdapterImages(oppImageList);
                }else if(arr[position].equals("Pescatarian")){
                    //consume dairy, eggs and fishes.
                    ImageList.clear();
                    oppImageList.clear();
                    ImageList.add(ok_dairy);
                    ImageList.add(ok_egg);
                    ImageList.add(no_poultry);
                    ImageList.add(ok_fish);
                    ImageList.add(no_pork);
                    ImageList.add(no_beef);

                    oppImageList.add(no_dairy);
                    oppImageList.add(no_egg);
                    oppImageList.add(ok_poultry);
                    oppImageList.add(no_fish);
                    oppImageList.add(ok_pork);
                    oppImageList.add(ok_beef);

                    myAdapter.setAdapterImages(ImageList);
                    myAdapter.setOppAdapterImages(oppImageList);

                }else if(arr[position].equals("Vegan")){
                    ImageList.clear();
                    oppImageList.clear();
                    addData();
                    myAdapter.setAdapterImages(oppImageList);
                    myAdapter.setOppAdapterImages(ImageList);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        //save
        binding.btnSave.setOnClickListener((View v) -> {
            //save database
            user.setUserName(binding.editTextName.getText().toString());
            user.setAge(binding.editTextAge.getText().toString());
            user.setHeight(binding.editTextHeight.getText().toString());
            user.setWeight(binding.editTextWeight.getText().toString());
            if(binding.switchGlutenFree.isChecked()){
                user.setGluten("true");
            }else{
                user.setGluten("false");
            }

            user.setVegeType(binding.spinnerTypeVegetarian.getSelectedItemPosition());

            //custom
            if(binding.spinnerTypeVegetarian.getSelectedItemPosition() == 1){
                ImageList = myAdapter.getAdapterImages();
                if(ImageList.get(0).getImgPic() == ok_dairy.getImgPic()){
                    user.setDairy("YES");
                }else{
                    user.setDairy("NO");
                }

                if(ImageList.get(1).getImgPic() == ok_egg.getImgPic()){
                    user.setEgg("YES");
                }else{
                    user.setEgg("NO");
                }

                if(ImageList.get(2).getImgPic() == ok_poultry.getImgPic()){
                    user.setPoultry("YES");
                }else{
                    user.setPoultry("NO");
                }

                if(ImageList.get(3).getImgPic() == ok_fish.getImgPic()){
                    user.setFish("YES");
                }else{
                    user.setFish("NO");
                }

                if(ImageList.get(4).getImgPic() == ok_pork.getImgPic()){
                    user.setPork("YES");
                }else{
                    user.setPork("NO");
                }

                if(ImageList.get(5).getImgPic() == ok_beef.getImgPic()){
                    user.setBeef("YES");
                }else{
                    user.setBeef("NO");
                }
            }

            db.updateUserInfo(user);

            Toast.makeText(this, "Save!", Toast.LENGTH_SHORT).show();

        });

    }

    private void addData(){

        ImageList.add(ok_dairy);
        ImageList.add(ok_egg);
        ImageList.add(ok_poultry);
        ImageList.add(ok_fish);
        ImageList.add(ok_pork);
        ImageList.add(ok_beef);


        oppImageList.add(no_dairy);
        oppImageList.add(no_egg);
        oppImageList.add(no_poultry);
        oppImageList.add(no_fish);
        oppImageList.add(no_pork);
        oppImageList.add(no_beef);

    }

    private void getUserInfoDatabase(){
        SharedPreferences preferences = getSharedPreferences("USER", MODE_PRIVATE);
        String userid = preferences.getString(getString(R.string.txtUserid),"");
        binding.editTextProfileUserId.setText(userid);
        myDBHelper = new MyDBHelper(ProfileActivity.this);
        db = new UserDBController(myDBHelper);

        user = db.selectUserInfo(userid);

        if(user.getUserName() != null){
            binding.editTextName.setText(user.getUserName());
        }

        if(user.getAge() != null){
            binding.editTextAge.setText(user.getAge());
        }

        if(user.getHeight() != null){
            binding.editTextHeight.setText(user.getHeight());
        }

        if(user.getWeight() != null){
            binding.editTextWeight.setText(user.getWeight());
        }

        if(user.getGluten() != null){
            boolean glutenfree = Boolean.parseBoolean(user.getGluten());
            binding.switchGlutenFree.setChecked(glutenfree);
        }

        binding.spinnerTypeVegetarian.setSelection(user.getVegeType());

        if(user.getEgg() == null){
            user.setEgg("YES");
        }

        if(user.getDairy() == null){
            user.setDairy("YES");
        }

        if(user.getPoultry() == null){
            user.setPoultry("YES");
        }

        if(user.getFish() == null){
            user.setFish("YES");
        }

        if(user.getPork() == null){
            user.setPork("YES");
        }

        if(user.getBeef() == null){
            user.setBeef("YES");
        }

    }

    @Override
    protected void onDestroy() {
        myDBHelper.close();
        super.onDestroy();
    }

    @Override
    public void onItemClick() {
        //custom
        if(binding.spinnerTypeVegetarian.getSelectedItemPosition() != 1){
            binding.spinnerTypeVegetarian.setSelection(1);
        }

    }




}