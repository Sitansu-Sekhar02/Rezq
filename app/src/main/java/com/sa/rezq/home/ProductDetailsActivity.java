package com.sa.rezq.home;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;

import com.sa.rezq.Activity.AppController;
import com.sa.rezq.R;
import com.sa.rezq.adapter.interfaces.OnVariantClickInvoke;
import com.sa.rezq.global.GlobalFunctions;
import com.sa.rezq.global.GlobalVariables;
import com.sa.rezq.services.model.CartPostModel;
import com.sa.rezq.services.model.FeedbackModel;
import com.sa.rezq.services.model.ProductDetailModel;
import com.sa.rezq.services.model.ProductImagesListModel;
import com.sa.rezq.services.model.ProductModel;
import com.sa.rezq.services.model.SpecificationModel;
import com.sa.rezq.services.model.VariantListModel;
import com.sa.rezq.services.model.VariantModel;
import com.vlonjatg.progressactivity.ProgressRelativeLayout;

import java.util.ArrayList;
import java.util.List;

public class ProductDetailsActivity extends AppCompatActivity implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener, OnVariantClickInvoke {
    public static final String TAG = "ProductDetActivity";
    public static final String BUNDLE_PRODUCT_MODEL = "BundleProductModel";
    public static final String BUNDLE_PRODUCT_ID = "BundleProductId";

    public static final int SLIDER_LOAD_TIME_IN_MILLI_SEC = 2000;

    Context context = null;
    static Activity activity = null;

    public static RelativeLayout cart_notification_layout;
    public View mainView;

    GlobalVariables globalVariables;
    GlobalFunctions globalFunctions;
    Window window = null;

    Toolbar toolbar;
    ActionBar actionBar;
    String mTitle;
    int mResourceID, titleResourseID;
    TextView toolbar_title;
    ImageView toolbar_icon;
    Menu menu;

    ProductModel productModel = null;
    String productId = null, variationId = null;
    String selectedVariantId = null,
            selectedSubVariantId = null;
    VariantModel selectedSubVariantModel = null;

    CartPostModel cartPostModel = null;
    String vendorId = null;

    TextView subCat_tv, category_tv, reviews_count_tv, variant_tv, price_tv, discount_tv, offer_tv, per_day_tv,
            per_week_tv, per_month_tv, seller_tv, seller_price_tv, view_details_tv, min_qty_tv, qty_tv, shipping_charges_tv, book_now_tv, add_to_cart_tv,
            sold_by_tv, review_view_more, product_details_tv, review_tv, terms_conditions_tv, variant_main_tv, variant_sub_tv, variant_subChild_tv,
            certification_tv, brand_name_tv, type_tv, weight_tv, dimension_tv, availability_tv, place_of_origin_tv, colour_tv, shipping_type_tv, product_description_tv;
    SliderLayout mProductSlider;
    PagerIndicator mPagerIndicator;
    ProductImagesListModel banners;
    RatingBar rating_rb;
    SwitchCompat add_manpower_sw;
    LinearLayout add_manpower_ll, product_details_ll, review_ll, review_bottom_ll, product_details_bottom_ll, terms_conditions_ll;
    ImageView minus_qty_iv, add_qty_iv;
    View product_details_line_view, review_line_view, terms_conditions_line_view;

    @Override
    public void onSliderClick(BaseSliderView slider) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void OnClickInvoke(int position, VariantModel variantModel, String pageType) {

    }

    //review list...
   /* ProgressRelativeLayout review_progressActivity;
    LinearLayoutManager reviewLayoutManager;
    RecyclerView review_recycler_view;
    ReviewListAdapter reviewListAdapter;
    List<FeedbackModel> reviewList = new ArrayList<>();

    //variant main list...
    LinearLayoutManager variant_mainLayoutManager;
    RecyclerView variant_main_rv;
    VariantListAdapter variantMainListAdapter;
    List<VariantModel> variantMainList = new ArrayList<>();

    //variant sub list...
    LinearLayoutManager variant_subLayoutManager;
    RecyclerView variant_sub_rv;
    VariantListAdapter variantSubListAdapter;
    List<VariantModel> variantSubMainList = new ArrayList<>();

    //variant sub child list...
    LinearLayoutManager variant_subChildLayoutManager;
    RecyclerView variant_subChild_rv;
    VariantListAdapter variantSubChildListAdapter;
    List<VariantModel> variantSubChildMainList = new ArrayList<>();

    //details list...
    ProgressRelativeLayout product_details_bottom_progressActivity;
    LinearLayoutManager productDetailsBottomLayoutManager;
    RecyclerView product_details_bottom_recycler_view;
    SpecificationListAdapter specificationListAdapter;
    List<SpecificationModel> specificationList = new ArrayList<>();

    VariantListModel variantListModel = null;

    ProductDetailModel productDetailModel = null;


    public static Intent newInstance(Activity activity, ProductModel productModel) {
        Intent intent = new Intent(activity, ProductDetailsActivity.class);
        Bundle args = new Bundle();
        args.putSerializable(BUNDLE_PRODUCT_MODEL, productModel);
        intent.putExtras(args);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.product_details_activity);

        context = this;
        activity = this;
        window = getWindow();

        globalFunctions = AppController.getInstance().getGlobalFunctions();
        globalVariables = AppController.getInstance().getGlobalVariables();

        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        //toolbar.setPadding(0, GlobalFunctions.getStatusBarHeight(context), 0, 0);
        //toolbar.setNavigationIcon(R.drawable.ic_back_draw);
        //toolbar.setContentInsetsAbsolute(0,0);
        toolbar_title = (TextView) toolbar.findViewById(R.id.toolbar_title);
       // toolbar_icon = (ImageView) toolbar.findViewById(R.id.toolbar_icon);
        toolbar_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        setOptionsMenuVisiblity(false);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimary));
        }

        variant_main_rv = (RecyclerView) findViewById(R.id.variant_main_rv);
        variant_mainLayoutManager = new LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false);

        variant_sub_rv = (RecyclerView) findViewById(R.id.variant_sub_rv);
        variant_subLayoutManager = new LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false);

        variant_subChild_rv = (RecyclerView) findViewById(R.id.variant_subChild_rv);
        variant_subChildLayoutManager = new LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false);

        review_recycler_view = (RecyclerView) findViewById(R.id.review_recycler_view);
        reviewLayoutManager = new LinearLayoutManager(context);
        review_progressActivity = (ProgressRelativeLayout) findViewById(R.id.review_progressActivity);

        product_details_bottom_recycler_view = (RecyclerView) findViewById(R.id.product_details_bottom_recycler_view);
        productDetailsBottomLayoutManager = new LinearLayoutManager(context);
        product_details_bottom_progressActivity = (ProgressRelativeLayout) findViewById(R.id.product_details_bottom_progressActivity);

        subCat_tv = (TextView) findViewById(R.id.subCat_tv);
        rating_rb = (RatingBar) findViewById(R.id.rating_rb);
        category_tv = (TextView) findViewById(R.id.category_tv);
        reviews_count_tv = (TextView) findViewById(R.id.reviews_count_tv);
        variant_tv = (TextView) findViewById(R.id.variant_tv);
        variant_main_tv = (TextView) findViewById(R.id.variant_main_tv);
        variant_sub_tv = (TextView) findViewById(R.id.variant_sub_tv);
        variant_subChild_tv = (TextView) findViewById(R.id.variant_subChild_tv);
        price_tv = (TextView) findViewById(R.id.price_tv);
        per_day_tv = (TextView) findViewById(R.id.per_day_tv);
        per_week_tv = (TextView) findViewById(R.id.per_week_tv);
        per_month_tv = (TextView) findViewById(R.id.per_month_tv);
        discount_tv = (TextView) findViewById(R.id.discount_tv);
        offer_tv = (TextView) findViewById(R.id.offer_tv);
        add_manpower_ll = (LinearLayout) findViewById(R.id.add_manpower_ll);
        add_manpower_sw = (SwitchCompat) findViewById(R.id.add_manpower_sw);
        seller_tv = (TextView) findViewById(R.id.seller_tv);
        seller_price_tv = (TextView) findViewById(R.id.seller_price_tv);
        view_details_tv = (TextView) findViewById(R.id.view_details_tv);
        min_qty_tv = (TextView) findViewById(R.id.min_qty_tv);
        qty_tv = (TextView) findViewById(R.id.qty_tv);
        shipping_charges_tv = (TextView) findViewById(R.id.shipping_charges_tv);
        minus_qty_iv = (ImageView) findViewById(R.id.minus_qty_iv);
        add_qty_iv = (ImageView) findViewById(R.id.add_qty_iv);
        book_now_tv = (TextView) findViewById(R.id.book_now_tv);
        add_to_cart_tv = (TextView) findViewById(R.id.add_to_cart_tv);
        sold_by_tv = (TextView) findViewById(R.id.sold_by_tv);
        review_view_more = (TextView) findViewById(R.id.review_view_more);
        product_details_tv = (TextView) findViewById(R.id.product_details_tv);
        review_tv = (TextView) findViewById(R.id.review_tv);
        terms_conditions_tv = (TextView) findViewById(R.id.terms_conditions_tv);

        certification_tv = (TextView) findViewById(R.id.certification_tv);
        brand_name_tv = (TextView) findViewById(R.id.brand_name_tv);
        type_tv = (TextView) findViewById(R.id.type_tv);
        weight_tv = (TextView) findViewById(R.id.weight_tv);
        dimension_tv = (TextView) findViewById(R.id.dimension_tv);
        availability_tv = (TextView) findViewById(R.id.availability_tv);
        place_of_origin_tv = (TextView) findViewById(R.id.place_of_origin_tv);
        colour_tv = (TextView) findViewById(R.id.colour_tv);
        shipping_type_tv = (TextView) findViewById(R.id.shipping_type_tv);
        product_description_tv = (TextView) findViewById(R.id.product_description_tv);

        product_details_ll = (LinearLayout) findViewById(R.id.product_details_ll);
        review_ll = (LinearLayout) findViewById(R.id.review_ll);
        review_bottom_ll = (LinearLayout) findViewById(R.id.review_bottom_ll);
        product_details_bottom_ll = (LinearLayout) findViewById(R.id.product_details_bottom_ll);
        terms_conditions_ll = (LinearLayout) findViewById(R.id.terms_conditions_ll);
        product_details_line_view = (View) findViewById(R.id.product_details_line_view);
        review_line_view = (View) findViewById(R.id.review_line_view);
        terms_conditions_line_view = (View) findViewById(R.id.terms_conditions_line_view);

        mProductSlider = (SliderLayout) findViewById(R.id.home_main_fragment_slider);
        mPagerIndicator = (PagerIndicator) findViewById(R.id.home_main_fragment_slider_custom_indicator);

        mainView = subCat_tv;

        if (getIntent().hasExtra(BUNDLE_PRODUCT_MODEL)) {
            productModel = (ProductModel) getIntent().getSerializableExtra(BUNDLE_PRODUCT_MODEL);
        } else {
            productModel = null;
        }

        if (productModel != null) {
            productId = productModel.getId();
            variationId = productModel.getVariation();
            toolbar_title.setText(productModel.getTitle());
        }

        if (cartPostModel == null) {
            cartPostModel = new CartPostModel();
        }
        cartPostModel.setVariationId(variationId);
        cartPostModel.setProductId(productId);

        add_to_cart_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (GlobalFunctions.isLoggedIn(activity)) {
                    String qty = qty_tv.getText().toString().trim();
                    if (!qty.equalsIgnoreCase("0")) {
                        cartPostModel.setQuantity(qty);
                        insertCart(context, cartPostModel);
                    } else {
                        GlobalFunctions.displayMessaage(context, mainView, activity.getString(R.string.add_quantity));
                    }
                } else {
                    openLoginAlertDialog(activity);
                }

            }
        });

        *//*wishList_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProductPostModel productPostModel = new ProductPostModel();
                productPostModel.setProductId(productId);
                getWishList(activity, productPostModel);
            }
        });*//*

        initAllLists();
        refreshList();

        setOnClickEvent(GlobalVariables.PAGE_TYPE_PRODUCT_DETAILS);

        sold_by_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = CompanyDetailsActivity.newInstance(activity, vendorId);
                startActivity(intent);
            }
        });


        product_details_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setOnClickEvent(GlobalVariables.PAGE_TYPE_PRODUCT_DETAILS);
            }
        });

        review_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setOnClickEvent(GlobalVariables.PAGE_TYPE_REVIEW);
            }
        });

        terms_conditions_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setOnClickEvent(GlobalVariables.PAGE_TYPE_TC);
            }
        });

        add_qty_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quantityCounter(qty_tv, true);
            }
        });

        minus_qty_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quantityCounter(qty_tv, false);
            }
        });

       *//* variant_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = ProductVariantListActivity.newInstance(activity, variantListModel);
                startActivityForResult(intent, GlobalVariables.REQUEST_CODE_FOR_VARIATION);
            }
        });*//*


    }

    private void initAllLists() {
        initReviewList();
        initDetailsList();

    }

    private void quantityCounter(TextView qty_ev, boolean isAddition) {
        String minimumQuantity = "0";
        try {
            if (qty_ev != null) {
                String val = qty_ev.getText().toString().trim();

                int qty = Integer.parseInt(val == null && val.equalsIgnoreCase("") ? minimumQuantity : val);
                if (isAddition) {
                    qty = qty + 1;
                } else {
                    if (qty > Integer.parseInt(minimumQuantity)) {
                        qty = qty - 1;
                    }
                }
                qty_ev.setText(qty + "");
            }
        } catch (Exception e) {
            GlobalFunctions.displayErrorDialog(activity, activity.getString(R.string.somethingWentWrong));
        }
    }

    private void setOnClickEvent(String type) {

        product_details_line_view.setBackgroundColor(globalFunctions.getColor(activity, R.color.transparent));
        review_line_view.setBackgroundColor(globalFunctions.getColor(activity, R.color.transparent));
        terms_conditions_line_view.setBackgroundColor(globalFunctions.getColor(activity, R.color.transparent));

        if (type.equalsIgnoreCase(GlobalVariables.PAGE_TYPE_PRODUCT_DETAILS)) {
            product_details_line_view.setBackgroundColor(globalFunctions.getColor(activity, R.color.button_yellow));
            terms_conditions_tv.setTextColor(activity.getResources().getColor(R.color.app_fontColor));
            review_tv.setTextColor(activity.getResources().getColor(R.color.app_fontColor));
            product_details_tv.setTextColor(activity.getResources().getColor(R.color.app_fontBlackColor));

            product_details_bottom_ll.setVisibility(View.VISIBLE);
            review_bottom_ll.setVisibility(View.GONE);
        } else if (type.equalsIgnoreCase(GlobalVariables.PAGE_TYPE_REVIEW)) {
            review_tv.setTextColor(activity.getResources().getColor(R.color.app_fontBlackColor));
            terms_conditions_tv.setTextColor(activity.getResources().getColor(R.color.app_fontColor));
            product_details_tv.setTextColor(activity.getResources().getColor(R.color.app_fontColor));
            review_line_view.setBackgroundColor(globalFunctions.getColor(activity, R.color.button_yellow));

            product_details_bottom_ll.setVisibility(View.GONE);
            review_bottom_ll.setVisibility(View.VISIBLE);

//            initAllLists();

        } else if (type.equalsIgnoreCase(GlobalVariables.PAGE_TYPE_TC)) {
            terms_conditions_tv.setTextColor(activity.getResources().getColor(R.color.app_fontBlackColor));
            product_details_tv.setTextColor(activity.getResources().getColor(R.color.app_fontColor));
            review_tv.setTextColor(activity.getResources().getColor(R.color.app_fontColor));
            terms_conditions_line_view.setBackgroundColor(globalFunctions.getColor(activity, R.color.button_yellow));

            product_details_bottom_ll.setVisibility(View.GONE);
            review_bottom_ll.setVisibility(View.GONE);

        }
    }


    private void refreshList() {
        getProductDetails(context);

    }

    private void getProductDetails(final Context context) {
        globalFunctions.showProgress(activity, getString(R.string.loading));
        ServicesMethodsManager servicesMethodsManager = new ServicesMethodsManager();
        servicesMethodsManager.getProductDetails(context, productId, variationId, new ServerResponseInterface() {
            @Override
            public void OnSuccessFromServer(Object arg0) {
                if (context != null) {
                    globalFunctions.hideProgress();
                    Log.d(TAG, "Response: " + arg0.toString());
                    ProductDetailMainModel model = (ProductDetailMainModel) arg0;
                    setUpPage(model.getProductDetailModel());

                    if (GlobalFunctions.isLoggedIn(activity)) {
                        getCart(activity);
                    }
                    ;
                }
            }

            @Override
            public void OnFailureFromServer(String msg) {
                if (context != null) {
                    globalFunctions.hideProgress();
                    Log.d(TAG, "Failure : " + msg);
                }
            }

            @Override
            public void OnError(String msg) {
                if (context != null) {
                    globalFunctions.hideProgress();
                    Log.d(TAG, "Error : " + msg);
                }
            }
        }, "GetSubCatList");
    }

    private void getCart(final Context context) {
//        globalFunctions.showProgress(activity, getString(R.string.loading));
        ServicesMethodsManager servicesMethodsManager = new ServicesMethodsManager();
        servicesMethodsManager.getCart(context, new ServerResponseInterface() {
            @Override
            public void OnSuccessFromServer(Object arg0) {
//                globalFunctions.hideProgress();
                Log.d(TAG, "Response: " + arg0.toString());

                CartMainModel model = (CartMainModel) arg0;
                CartModel cartModel = model.getCartModel();
                if (cartModel.getSalesCount() != null) {
                    globalFunctions.setCartCount(context, cartModel.getSalesCount());
                    setCartCount(cartModel.getSalesCount());
                }
            }

            @Override
            public void OnFailureFromServer(String msg) {
//                globalFunctions.hideProgress();
//                showErrorPage();
                Log.d(TAG, "Failure : " + msg);
            }

            @Override
            public void OnError(String msg) {
//                globalFunctions.hideProgress();
//                showErrorPage();
                Log.d(TAG, "Error : " + msg);
            }
        }, "GetCart");
    }

    private void insertCart(final Context context, CartPostModel cartPostModel) {
        globalFunctions.showProgress(activity, getString(R.string.loading));
        ServicesMethodsManager servicesMethodsManager = new ServicesMethodsManager();
        servicesMethodsManager.insertCart(context, cartPostModel, new ServerResponseInterface() {
            @Override
            public void OnSuccessFromServer(Object arg0) {
                if (context != null) {
                    globalFunctions.hideProgress();
                    Log.d(TAG, "Response: " + arg0.toString());
                    StatusMainModel model = (StatusMainModel) arg0;
                    validateInsertCartOutput(model);
                }
            }

            @Override
            public void OnFailureFromServer(String msg) {
                if (context != null) {
                    globalFunctions.hideProgress();
                    Log.d(TAG, "Failure : " + msg);
                }
            }

            @Override
            public void OnError(String msg) {
                if (context != null) {
                    globalFunctions.hideProgress();
                    Log.d(TAG, "Error : " + msg);
                }
            }
        }, "GetSubCatList");
    }

    private void validateInsertCartOutput(StatusMainModel model) {
        StatusModel statusModel = model.getStatusModel();
        GlobalFunctions.displayMessaage(activity, mainView, statusModel.getMessage());
        if (model.isStatus()) {
            if (statusModel.getCartCount() != null) {
                globalFunctions.setCartCount(context, statusModel.getCartCount());
                setCartCount(statusModel.getCartCount());
            }
        } else {
//            GlobalFunctions.displayMessaage(activity, mainView, model.getStatusMessage());
        }
    }

    private void setUpPage(ProductDetailModel detailModel) {
        if (detailModel != null) {
            setUpMainVariantList(detailModel.getVariantListModel());
            setOfferList(detailModel);
            setFieldValue(detailModel);
            invalidateOptionsMenu();
        }
    }

    private void setUpMainVariantList(VariantListModel variantListModel) {
        if (variantListModel != null) {
            if (variantListModel.getVariantList() != null) {
                variantMainList.clear();
                variantMainList.addAll(variantListModel.getVariantList());
                if (variantMainListAdapter != null) {
                    synchronized (variantMainListAdapter) {
                        variantMainListAdapter.notifyDataSetChanged();
                    }
                }
                if (variantMainList.size() <= 0) {
                    variant_main_rv.setVisibility(View.GONE);
                } else {
                    if (GlobalFunctions.isNotNullValue(variantMainList.get(0).getTitle())) {
                        variant_main_tv.setText(variantMainList.get(0).getTitle());
                    } else {
                        variant_main_tv.setText(activity.getString(R.string.variant));
                    }
                    variant_main_rv.setVisibility(View.VISIBLE);
                    initVariantMainList();

                    VariantModel variantModel=GlobalFunctions.getSelectedModelFromList(variantMainList);
                    if (variantModel.getVariant2ListModel()!=null){
                        if (variantModel.getVariant2ListModel().getVariantList().size()>0){
                            setUpSubVariantList(variantModel.getVariant2ListModel());
                        }else {
                            variant_sub_rv.setVisibility(View.GONE);
                            variant_sub_tv.setVisibility(View.GONE);
                        }
                    }
                }
            }
        }
    }



    private void setFieldValue(ProductDetailModel detailModel) {
        if (detailModel != null) {
            this.productDetailModel = detailModel;

            if (globalFunctions.isNotNullValue(detailModel.getDiscount()) && globalFunctions.isNotZeroValue(detailModel.getDiscount())) {
                price_tv.setText(GlobalFunctions.getTotalPrice(detailModel.getPrice(), detailModel.getDiscount()) + " " + detailModel.getCurrency());
                offer_tv.setText(GlobalFunctions.getTotalPricePercentage(detailModel.getPrice(), detailModel.getDiscount()) + "% " + activity.getString(R.string.off));
                discount_tv.setText(detailModel.getPrice() + " " + detailModel.getCurrency());
                discount_tv.setPaintFlags(discount_tv.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                discount_tv.setVisibility(View.GONE);
                offer_tv.setVisibility(View.GONE);
            } else if (globalFunctions.isNotNullValue(detailModel.getPrice())) {
                price_tv.setText(detailModel.getPrice() + " " + detailModel.getCurrency());
                discount_tv.setVisibility(View.GONE);
                offer_tv.setVisibility(View.GONE);
            } else {
                discount_tv.setVisibility(View.GONE);
                offer_tv.setVisibility(View.GONE);
            }

            if (globalFunctions.isNotNullValue(detailModel.getTitle())) {
                subCat_tv.setText(detailModel.getTitle());
            }

            if (globalFunctions.isNotNullValue(detailModel.getCategory())) {
                category_tv.setText(detailModel.getCategory());
            }

            if (globalFunctions.isNotNullValue(detailModel.getRating())) {
                rating_rb.setRating(GlobalFunctions.getRatingValue(detailModel.getRating()));
            }
            if (globalFunctions.isNotNullValue(detailModel.getRatingCount())) {
                reviews_count_tv.setText(detailModel.getRatingCount() + " " + activity.getResources().getString(R.string.reviews));
            }
            if (globalFunctions.isNotNullValue(detailModel.getMinQuantity())) {
                min_qty_tv.setText("(Min " + detailModel.getMinQuantity() + " unit)");
            }

            if (globalFunctions.isNotNullValue(detailModel.getSoldBy())) {
                sold_by_tv.setText(detailModel.getSoldBy());
            }

            if (globalFunctions.isNotNullValue(detailModel.getVendor())) {
                vendorId = detailModel.getVendor();
            }

            if (detailModel.getFeedbackListModel() != null) {
                setFeedbackList(detailModel.getFeedbackListModel());
            }

            if (detailModel.getSpecificationListModel() != null) {
                setDetailsList(detailModel.getSpecificationListModel());
            }

            *//*if (detailModel.getVariantListModel() != null) {
                if (detailModel.getVariantListModel().getVariantList().size() > 0) {
                    if (detailModel.getVariantListModel().getVariantList().get(0).getVariant2ListModel() != null) {
                        this.variantListModel = detailModel.getVariantListModel().getVariantList().get(0).getVariant2ListModel();
                    }
//                    String variantTitle = GlobalFunctions.getSelectedIdNameFromList(detailModel.getVariantListModel().getVariantList());
//                    String subVariantTitle = GlobalFunctions.getSelectedSubIdNameFromList(detailModel.getVariantListModel().getVariantList());
//                    variant_tv.setText(variantTitle + " " + subVariantTitle);
                }
            }*//*

//            if (globalFunctions.isNotNullValue(detailModel.getDiscount())) {
//                shipping_charges_tv.setText(detailModel.getDiscount());
//            }

        }
    }

    private void setFeedbackList(FeedbackListModel feedbackListModel) {
        if (feedbackListModel.getFeedbackList() != null) {
            reviewList.clear();
            reviewList.addAll(feedbackListModel.getFeedbackList());
            if (reviewList.size() > 0) {
//                review_recycler_view.setVisibility(View.VISIBLE);
                initReviewList();
                showVariantContent();
            } else {
                showVariantEmptyPage();
//                review_recycler_view.setVisibility(View.GONE);
            }
        } else {
            showVariantEmptyPage();
//            review_recycler_view.setVisibility(View.GONE);
        }
    }

    private void setDetailsList(SpecificationListModel specificationListModel) {
        if (specificationListModel.getSpecificationList() != null) {
            specificationList.clear();
            specificationList.addAll(specificationListModel.getSpecificationList());
            if (specificationList.size() > 0) {
//                review_recycler_view.setVisibility(View.VISIBLE);
                initDetailsList();
                showDetailsContent();
            } else {
                showDetailsEmptyPage();
//                review_recycler_view.setVisibility(View.GONE);
            }
        } else {
            showDetailsEmptyPage();
//            review_recycler_view.setVisibility(View.GONE);
        }
    }

    *//*private void setVariantList(ProductDetailModel detailModel) {
        if (detailModel.getVariantListModel() != null) {
            variantList.clear();
            variantList.addAll(detailModel.getVariantListModel().getVariantList());
            if (variantList.size() > 0) {
                variant_recycler_view.setVisibility(View.VISIBLE);
                available_tv.setVisibility(View.VISIBLE);

                available_tv.setText(activity.getString(R.string.available) + " " + variantList.get(0).getTitle());
                getSelectedId(variantList);

                if (selectedVariantId != null) {
                    initVariantList();
                    showVariantContent();
                    getSelectedSubId(selectedSubVariantModel.getVariantListModel().getVariantList());
                    initSubVariantList();
                }

            } else {
                available_tv.setVisibility(View.GONE);
                showVariantEmptyPage();
//                variant_recycler_view.setVisibility(View.GONE);
            }
        } else {
            available_tv.setVisibility(View.GONE);
            showVariantEmptyPage();
        }
    }*//*


    private void setOfferList(ProductDetailModel detailModel) {
        if (detailModel.getProductImagesList() != null) {
            setBanners(detailModel.getProductImagesList());
        }
    }

    public void initReviewList() {
        review_recycler_view.setLayoutManager(reviewLayoutManager);
        review_recycler_view.setHasFixedSize(true);
        reviewLayoutManager.onSaveInstanceState();
        Parcelable state = reviewLayoutManager.onSaveInstanceState();
        reviewListAdapter = new ReviewListAdapter(activity, reviewList);
        review_recycler_view.setAdapter(reviewListAdapter);
        reviewLayoutManager.onRestoreInstanceState(state);
    }

    public void initDetailsList() {
        product_details_bottom_recycler_view.setLayoutManager(productDetailsBottomLayoutManager);
        product_details_bottom_recycler_view.setHasFixedSize(true);
        productDetailsBottomLayoutManager.onSaveInstanceState();
        Parcelable state = productDetailsBottomLayoutManager.onSaveInstanceState();
        specificationListAdapter = new SpecificationListAdapter(activity, specificationList);
        product_details_bottom_recycler_view.setAdapter(specificationListAdapter);
        productDetailsBottomLayoutManager.onRestoreInstanceState(state);
    }

    public void initVariantMainList() {
        variant_main_rv.setLayoutManager(variant_mainLayoutManager);
        variantMainListAdapter = new VariantListAdapter(activity, variantMainList, "0",GlobalVariables.PAGE_TYPE_MATERIAL, this);
        variant_main_rv.setAdapter(variantMainListAdapter);
    }

    public void initVariantSubList() {
        variant_sub_rv.setLayoutManager(variant_subLayoutManager);
        variantSubListAdapter = new VariantListAdapter(activity, variantSubMainList, "0",GlobalVariables.PAGE_TYPE_CAT, this);
        variant_sub_rv.setAdapter(variantSubListAdapter);
    }

    public void initVariantSubChildList() {
        variant_subChild_rv.setLayoutManager(variant_subChildLayoutManager);
        variantSubChildListAdapter = new VariantListAdapter(activity, variantSubChildMainList, "0",GlobalVariables.PAGE_TYPE_SUB_CAT, this);
        variant_subChild_rv.setAdapter(variantSubChildListAdapter);
    }

    private void showVariantContent() {
        if (review_progressActivity != null) {
            review_progressActivity.showContent();
        }
    }

    private void showDetailsContent() {
        if (product_details_bottom_progressActivity != null) {
            product_details_bottom_progressActivity.showContent();
        }
    }

    private void showVariantEmptyPage() {
        if (review_progressActivity != null) {
            review_progressActivity.showEmpty(getResources().getDrawable(R.drawable.ic_empty), getString(R.string.emptyTitle),
                    "");
        }

    }

    private void showDetailsEmptyPage() {
        if (product_details_bottom_progressActivity != null) {
            product_details_bottom_progressActivity.showEmpty(getResources().getDrawable(R.drawable.ic_empty), getString(R.string.emptyTitle),
                    "");
        }

    }

    private void setBanners(ProductImagesListModel bannerListModel) {
        if (bannerListModel != null) {
            if (banners == null) {
                BannerTask bannerTask = new BannerTask();
                bannerTask.execute(bannerListModel);
            } else if (banners.getProductImageList().size() != bannerListModel.getProductImageList().size()) {
                BannerTask bannerTask = new BannerTask();
                bannerTask.execute(bannerListModel);
            }
        }
    }


    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
//        setTitle(productModel.getBrand(), 0, 0);
        super.onResume();
    }

    public void setTitle(String title, int titleImageID, int backgroundResourceID) {
        mTitle = title;
        if (backgroundResourceID != 0) {
            mResourceID = backgroundResourceID;
        } else {
            mResourceID = 0;
        }
        if (titleImageID != 0) {
            titleResourseID = titleImageID;
        } else {
            titleResourseID = 0;
        }
        restoreToolbar();
    }

    private void restoreToolbar() {
        //toolbar = (Toolbar) findViewById(R.id.tool_bar);
        Log.d(TAG, "Restore Tool Bar");
        if (actionBar != null) {
            Log.d(TAG, "Restore Action Bar not Null");
            Log.d(TAG, "Title : " + mTitle);
            if (titleResourseID != 0) {
                toolbar_title.setVisibility(View.GONE);
            } else {
                toolbar_title.setVisibility(View.VISIBLE);
                toolbar_title.setText(mTitle);
            }


            if (mResourceID != 0) toolbar.setBackgroundResource(mResourceID);
            //actionBar.setTitle("");
            //actionBar.setDisplayHomeAsUpEnabled(true);
        }

    }

    public static void setCartCount(final String cartCount) {
        if (cart_notification_layout != null) {
            final TextView tv = (TextView) cart_notification_layout.findViewById(R.id.actionbar_badge_cart_textview);
            if (activity != null) {
                if (cartCount.equalsIgnoreCase("0")) {
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tv.setVisibility(View.GONE);
                        }
                    });

                } else {
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tv.setVisibility(View.VISIBLE);
                            tv.setText(cartCount);
                        }
                    });
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.clear();
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main_new, menu);
        this.menu = menu;
        setOptionsMenuVisiblity(false);
        MenuItem item = menu.findItem(R.id.action_cart);
        MenuItem wish_item = menu.findItem(R.id.action_wishlist);

        if (productDetailModel != null && productDetailModel.getWishlist() != null && productDetailModel.getWishlist().equalsIgnoreCase("1")) {
            wish_item.setIcon(activity.getResources().getDrawable(R.drawable.ic_heart));
        } else {
            wish_item.setIcon(activity.getResources().getDrawable(R.drawable.ic_menu_wishlist));
        }
        MenuItemCompat.setActionView(item, R.layout.actionbar_badge_cart_layout);
        cart_notification_layout = (RelativeLayout) MenuItemCompat.getActionView(item);
        cart_notification_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (GlobalFunctions.isLoggedIn(activity)) {
                    Intent intent = new Intent(context, CartActivity.class);
                    activity.startActivity(intent);
                } else {
                    openLoginAlertDialog(activity);
                }
            }
        });


        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.clear();
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main_new, menu);
        this.menu = menu;
        setOptionsMenuVisiblity(false);
        MenuItem item = menu.findItem(R.id.action_cart);
        MenuItem wish_item = menu.findItem(R.id.action_wishlist);

        if (productDetailModel != null && productDetailModel.getWishlist() != null && productDetailModel.getWishlist().equalsIgnoreCase("1")) {
            wish_item.setIcon(activity.getResources().getDrawable(R.drawable.ic_heart));
        } else {
            wish_item.setIcon(activity.getResources().getDrawable(R.drawable.ic_menu_wishlist));
        }

        MenuItemCompat.setActionView(item, R.layout.actionbar_badge_cart_layout);
        cart_notification_layout = (RelativeLayout) MenuItemCompat.getActionView(item);
        cart_notification_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (GlobalFunctions.isLoggedIn(activity)) {
                    Intent intent = new Intent(context, CartActivity.class);
                    activity.startActivity(intent);
                } else {
                    openLoginAlertDialog(activity);
                }
            }
        });

        return true;
    }

    public void setOptionsMenuVisiblity(boolean showMenu) {
        if (menu == null)
            return;
        //menu.setGroupVisible(R.id.menu, showMenu);
    }

    private void openLoginAlertDialog(Context mainContext) {
        final AlertDialog alertDialog = new AlertDialog(activity);
        alertDialog.setCancelable(false);
        alertDialog.setIcon(R.drawable.app_icon);
        alertDialog.setTitle(activity.getString(R.string.app_name));
        alertDialog.setMessage(activity.getResources().getString(R.string.continue_to_login));
        alertDialog.setPositiveButton(activity.getString(R.string.yes), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                Intent intent = new Intent(activity, LoginActivity.class);
                startActivity(intent);
                activity.finish();
            }
        });

        alertDialog.setNegativeButton(activity.getString(R.string.no), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        alertDialog.show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_share:
//                Intent myIntent = new Intent(activity, SearchActivity.class);
//                startActivityForResult(myIntent, globalVariables.REQUEST_CODE_FOR_SEARCH);
                break;

            case R.id.action_wishlist:

                if (GlobalFunctions.isLoggedIn(activity)) {
                    addToWishList(context);
                } else {
                    openLoginAlertDialog(activity);
                }

                break;
        }
        return false;
    }

    private void addToWishList(Context context) {
        WishlistPostModel wishlistPostModel = new WishlistPostModel();
        wishlistPostModel.setProductId(productDetailModel.getId());
        wishlistPostModel.setVariationId(productDetailModel.getVariation());
        if (productDetailModel != null && productDetailModel.getWishlist() != null && productDetailModel.getWishlist().equalsIgnoreCase("1")) {
            wishlistPostModel.setStatus("2");
        } else {
            wishlistPostModel.setStatus("1");
        }
        addWishList(context, wishlistPostModel);
    }

    private void addWishList(final Context context, final WishlistPostModel wishlistPostModel) {
        GlobalFunctions.showProgress(context, getString(R.string.loading));
        ServicesMethodsManager servicesMethodsManager = new ServicesMethodsManager();
        servicesMethodsManager.removeWishList(context, wishlistPostModel, new ServerResponseInterface() {
            @Override
            public void OnSuccessFromServer(Object arg0) {
                GlobalFunctions.hideProgress();
                Log.d(TAG, "Response : " + arg0.toString());
                StatusMainModel model = (StatusMainModel) arg0;
                StatusModel statusModel = model.getStatusModel();
                GlobalFunctions.displayMessaage(activity, mainView, statusModel.getMessage());
                if (model.isStatus()) {
                    getProductDetails(context);
                }
            }

            @Override
            public void OnFailureFromServer(String msg) {
                GlobalFunctions.hideProgress();
                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                //GlobalFunctions.displayMessaage(context, mainView, msg);
                Log.d(TAG, "Failure : " + msg);
            }

            @Override
            public void OnError(String msg) {
                GlobalFunctions.hideProgress();
                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                //GlobalFunctions.displayMessaage(context, mainView, msg);
                Log.d(TAG, "Error : " + msg);
            }
        }, "UpdateCart");
    }


    public static void closeThisActivity() {
        if (activity != null) {
            activity.finish();
            //activity.overridePendingTransition(R.anim.zoom_in,R.anim.zoom_out);
        }
    }

    @Override
    public void onBackPressed() {
        closeThisActivity();
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {
        BannerModel bannerClickedModel = (BannerModel) slider.getBundle().getSerializable("data");
        if (bannerClickedModel != null) {
            *//*if (bannerClickedModel.getType() != null && bannerClickedModel.getId() != null) {
                if (bannerClickedModel.getType().equalsIgnoreCase(globalVariables.TYPE_CATEGORY)) {
                    Intent intent = ProductListActivity.newInstance(activity, bannerClickedModel.getId(), null, null);
                    activity.startActivity(intent);
                } else if (bannerClickedModel.getType().equalsIgnoreCase(globalVariables.TYPE_SUB_CATEGORY)) {
                    Intent intent = ProductListActivity.newInstance(activity, null, bannerClickedModel.getId(), null);
                    activity.startActivity(intent);
                } else if (bannerClickedModel.getType().equalsIgnoreCase(globalVariables.TYPE_OFFER)) {
                    Intent intent = ProductListActivity.newInstance(activity, null, null, bannerClickedModel.getId());
                    activity.startActivity(intent);
                } else if (bannerClickedModel.getType().equalsIgnoreCase(globalVariables.TYPE_NORMAL)) {
                    if (bannerClickedModel.getUrl() != null) {
                        try {
                            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(bannerClickedModel.getUrl()));
                            startActivity(browserIntent);
                        } catch (ActivityNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                }

            }*//*
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public class BannerTask extends AsyncTask<ProductImagesListModel, Void, List<String>> {

        @Override
        protected List<String> doInBackground(ProductImagesListModel... bannerListModels) {
            ProductImagesListModel bannerListModel = bannerListModels[0];
            if (bannerListModel != null) banners = bannerListModel;
            List<String> bannerStrings = new ArrayList<>();
            if (banners != null) {
                for (int i = 0; i < banners.getProductImageList().size(); i++) {
                    String imageModel = banners.getProductImageList().get(i).getImage();
                    //if url is not empty...add url...else don't add...
                    if (GlobalFunctions.isNotNullValue(imageModel)) {
                        bannerStrings.add(imageModel);
                    }
                }
            }
            if (mProductSlider != null) mProductSlider.removeAllSliders();
            return bannerStrings;
        }

        @Override
        protected void onPostExecute(List<String> bannerStrings) {
            super.onPostExecute(bannerStrings);
            setImageOnView(bannerStrings);
        }
    }

    public void setImageOnView(List<String> displayImageURL) {
        if (displayImageURL != null) {
            if (mProductSlider.getChildCount() <= 1) {
                Log.d(TAG, "ImageURL List size : " + displayImageURL.size());

                for (int i = 0; i < displayImageURL.size() && displayImageURL.get(i) != null; i++) {
                    CustomSliderTextView textSliderView = new CustomSliderTextView(context);
                    // initialize a SliderLayout
                    textSliderView
                            .description(1 + "")
                            .image(displayImageURL.get(i).toString())
                            .setScaleType(BaseSliderView.ScaleType.Fit)
                            .setOnSliderClickListener(this);
                    //add your extra information
                    textSliderView.bundle(new Bundle());
                    textSliderView.getBundle()
                            .putString("extra", banners.getProductImageList().get(i).getImage() + "");
                    textSliderView.getBundle()
                            .putSerializable("data", banners.getProductImageList().get(i));

                    mProductSlider.addSlider(textSliderView);
                    if (displayImageURL.size() > 0) {
                        mProductSlider.setCurrentPosition(0);
                    }

                }

                if (displayImageURL.size() > 0) {
                    mProductSlider.setPresetTransformer(SliderLayout.Transformer.Stack);
                    mProductSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
                    if (mPagerIndicator != null) {
                        mProductSlider.setCustomIndicator(mPagerIndicator);
                        mProductSlider.setCurrentPosition(0);
                    }
                    mProductSlider.setCustomAnimation(new DescriptionAnimation());
                    mProductSlider.setDuration(SLIDER_LOAD_TIME_IN_MILLI_SEC);
                    mProductSlider.addOnPageChangeListener(this);
                    mProductSlider.startAutoCycle();
                }
            }
        }
    }

    @Override
    public void OnClickInvoke(int position, VariantModel variantModel,String pageType) {
        variationId = variantModel.getVarId();
//        getProductDetails(context);

        if (pageType.equalsIgnoreCase(GlobalVariables.PAGE_TYPE_MATERIAL)){
            if (variantModel.getVariant2ListModel()!=null){
                if (variantModel.getVariant2ListModel().getVariantList().size()>0){
                    setUpSubVariantList(variantModel.getVariant2ListModel());
                }else {
                    getProductDetails(context);
                    variant_sub_rv.setVisibility(View.GONE);
                    variant_sub_tv.setVisibility(View.GONE);
                }
            }else {
                getProductDetails(context);
            }
        }else if (pageType.equalsIgnoreCase(GlobalVariables.PAGE_TYPE_CAT)){
            variationId = variantModel.getVarId();
            if (variantModel.getVariant3ListModel()!=null){
                if (variantModel.getVariant3ListModel().getVariantList().size()>0){
                    setUpSubChildVariantList(variantModel.getVariant3ListModel());
                }else {
                    getProductDetails(context);
                    variant_subChild_rv.setVisibility(View.GONE);
                    variant_subChild_tv.setVisibility(View.GONE);
                }
            }else {
                getProductDetails(context);
            }
        }else if (pageType.equalsIgnoreCase(GlobalVariables.PAGE_TYPE_SUB_CAT)){
            variationId = variantModel.getVarId();
            getProductDetails(context);

        }

    }

    private void setUpSubVariantList(VariantListModel variantListModel) {
        if (variantListModel != null) {
            if (variantListModel.getVariantList() != null) {
                variantSubMainList.clear();
                variantSubMainList.addAll(variantListModel.getVariantList());
                if (variantSubListAdapter != null) {
                    synchronized (variantSubListAdapter) {
                        variantSubListAdapter.notifyDataSetChanged();
                    }
                }
                if (variantSubMainList.size() <= 0) {
                    variant_sub_rv.setVisibility(View.GONE);
                    variant_sub_tv.setVisibility(View.GONE);
                } else {
                    if (GlobalFunctions.isNotNullValue(variantSubMainList.get(0).getTitle())) {
                        variant_sub_tv.setText(variantSubMainList.get(0).getTitle());
                    } else {
                        variant_sub_tv.setText(activity.getString(R.string.variant));
                    }
                    variant_sub_rv.setVisibility(View.VISIBLE);
                    variant_sub_tv.setVisibility(View.VISIBLE);
                    initVariantSubList();

                    VariantModel variantModel=GlobalFunctions.getSelectedModelFromList(variantSubMainList);
                    if (variantModel.getVariant3ListModel()!=null){
                        if (variantModel.getVariant3ListModel().getVariantList().size()>0){
                            setUpSubChildVariantList(variantModel.getVariant3ListModel());
                        }else {
                            variant_subChild_rv.setVisibility(View.GONE);
                            variant_subChild_tv.setVisibility(View.GONE);
                        }
                    }
                }
            }
        }
    }

    private void setUpSubChildVariantList(VariantListModel variantListModel) {
        if (variantListModel != null) {
            if (variantListModel.getVariantList() != null) {
                variantSubChildMainList.clear();
                variantSubChildMainList.addAll(variantListModel.getVariantList());
                if (variantSubChildListAdapter != null) {
                    synchronized (variantSubChildListAdapter) {
                        variantSubChildListAdapter.notifyDataSetChanged();
                    }
                }
                if (variantSubChildMainList.size() <= 0) {
                    variant_subChild_rv.setVisibility(View.GONE);
                    variant_subChild_tv.setVisibility(View.GONE);
                } else {
                    if (GlobalFunctions.isNotNullValue(variantSubChildMainList.get(0).getTitle())) {
                        variant_subChild_tv.setText(variantSubChildMainList.get(0).getTitle());
                    } else {
                        variant_subChild_tv.setText(activity.getString(R.string.variant));
                    }
                    variant_subChild_rv.setVisibility(View.VISIBLE);
                    variant_subChild_tv.setVisibility(View.VISIBLE);
                    initVariantSubChildList();
                }
            }
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == activity.RESULT_OK) {
            if (requestCode == globalVariables.REQUEST_CODE_FOR_SEARCH) {
                SearchResponseModel searchResponseModel = (SearchResponseModel) data.getExtras().getSerializable(SearchActivity.BUNDLE_SEARCH_RESPONSE_MODEL);
                if (searchResponseModel != null) {
                    if (searchResponseModel != null) {
                        if (searchResponseModel.getExtra() != null && searchResponseModel.getId() != null) {
                            if (searchResponseModel.getExtra().equalsIgnoreCase(globalVariables.SEARCH_TYPE_CATEGORY)) {
                                CategoryModel categoryModel = new CategoryModel();
                                categoryModel.setId(searchResponseModel.getId());
                                categoryModel.setTitle(searchResponseModel.getTitle());
                                Intent intent = CategoryListActivity.newInstance(activity, categoryModel);
                                activity.startActivity(intent);
                            } else if (searchResponseModel.getExtra().equalsIgnoreCase(globalVariables.SEARCH_TYPE_SUB_CATEGORY)) {
                                CategoryModel categoryModel = new CategoryModel();
                                categoryModel.setId(searchResponseModel.getId());
                                categoryModel.setTitle(searchResponseModel.getTitle());
                                if (searchResponseModel.getVariation() != null) {
                                    String categoryId = searchResponseModel.getVariation();
                                    Intent intent = SubCatListActivity.newInstance(activity, categoryId, categoryModel);
                                    activity.startActivity(intent);
                                }
                            } else if (searchResponseModel.getExtra().equalsIgnoreCase(globalVariables.SEARCH_TYPE_PRODUCT)) {
                                SubCategoryModel subCategoryModel = new SubCategoryModel();
                                subCategoryModel.setId(searchResponseModel.getId());
                                subCategoryModel.setTitle(searchResponseModel.getTitle());
                                Intent intent = ProductListActivity.newInstance(activity, subCategoryModel);
                                activity.startActivity(intent);

                            } else if (searchResponseModel.getExtra().equalsIgnoreCase(globalVariables.SEARCH_TYPE_PRODUCT_DETAIL)) {
                                ProductModel productModel = new ProductModel();
                                productModel.setId(searchResponseModel.getId());
                                productModel.setTitle(searchResponseModel.getTitle());
                                productModel.setVariation(searchResponseModel.getVariation());
                                Intent intent = com.saudi.vendor.souqbina.flows.product_list.ProductDetailsActivity.newInstance(activity, productModel);
                                activity.startActivity(intent);
                            }
                        }
                    }
                }
            } else if (requestCode == globalVariables.REQUEST_CODE_FOR_VARIATION) {
                VariantModel variantModel = (VariantModel) data.getExtras().getSerializable(ProductVariantListActivity.BUNDLE_VARIANT_MODEL);
                if (variantModel != null) {
                    this.variationId = variantModel.getVarId();
                    getProductDetails(context);

                }
            }
        }

    }*/
}

