package com.sa.rezq.services;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import com.sa.rezq.Activity.AppController;
import com.sa.rezq.R;
import com.sa.rezq.global.GlobalFunctions;
import com.sa.rezq.global.GlobalVariables;
import com.sa.rezq.services.model.LatlongModel;

import com.sa.rezq.services.model.*;

import org.json.JSONObject;


public class ServicesMethodsManager {

    public static final String TAG = "ServicesMethodsMgr";
    private ServerResponseInterface mUiCallBack;

    GlobalFunctions globalFunctions;
    GlobalVariables globalVariables;

    public ServicesMethodsManager() {
        globalFunctions = AppController.getInstance().getGlobalFunctions();
        globalVariables = AppController.getInstance().getGlobalVariables();
    }


    private void setCallbacks(ServerResponseInterface mCallBack) {
        mUiCallBack = mCallBack;
    }

    private void postData(final Context context, final Object obj, String URL, String query, String TAG, final boolean isCookieSave) {
        /*if(obj instanceof LoginModel){
            String token =  GlobalFunctions.getSharedPreferenceString(context, GlobalVariables.SHARED_PREFERENCE_TOKEN);
            if(token!=null){URL += "?"+"token="+ token;}
        }*/

        final VolleyServices request = new VolleyServices(context);
        request.setCallbacks(new VolleyServices.ResposeCallBack() {
            @Override
            public void OnSuccess(JSONObject arg0) {
                if (isCookieSave) {
                    GlobalFunctions.setSharedPreferenceString(context, GlobalVariables.SHARED_PREFERENCE_COOKIE, request.getCookie());
                }
                parseResponse(context, obj, arg0);
            }

            @Override
            public void OnFailure(int cause) {
                mUiCallBack.OnFailureFromServer(context.getString(cause));
            }

            @Override
            public void OnFailure(String cause) {
                mUiCallBack.OnFailureFromServer(cause);
            }
        });
        request.setBody(obj.toString());
        request.makeJsonPostRequest(context, URL, query, TAG);
    }

    private void postData(final Context context, final Object obj, String URL, String query, String TAG) {
        /*if(obj instanceof LoginModel){
            String token =  GlobalFunctions.getSharedPreferenceString(context, GlobalVariables.SHARED_PREFERENCE_TOKEN);
            if(token!=null){URL += "?"+"token="+ token;}
        }*/

        final VolleyServices request = new VolleyServices(context);
        request.setCallbacks(new VolleyServices.ResposeCallBack() {
            @Override
            public void OnSuccess(JSONObject arg0) {
                if (obj instanceof HomeIndexModel) {
                    GlobalFunctions.setSharedPreferenceString(context, GlobalVariables.SHARED_PREFERENCE_COOKIE, request.getCookie());
                }
                parseResponse(context, obj, arg0);
            }

            @Override
            public void OnFailure(int cause) {
                mUiCallBack.OnFailureFromServer(context.getString(cause));
            }

            @Override
            public void OnFailure(String cause) {
                mUiCallBack.OnFailureFromServer(cause);
            }
        });
        request.setBody(obj.toString());
        request.makeJsonPostRequest(context, URL, query, TAG);
    }

    private void postData(final Context context, final Object obj, String URL, String TAG) {
        final VolleyServices request = new VolleyServices(context);
        request.setCallbacks(new VolleyServices.ResposeCallBack() {
            @Override
            public void OnSuccess(JSONObject arg0) {
                if (obj instanceof HomeIndexModel) {
                    GlobalFunctions.setSharedPreferenceString(context, GlobalVariables.SHARED_PREFERENCE_COOKIE, request.getCookie());
                }
                parseResponse(context, obj, arg0);
            }

            @Override
            public void OnFailure(int cause) {
                mUiCallBack.OnFailureFromServer(context.getString(cause));
            }

            @Override
            public void OnFailure(String cause) {
                mUiCallBack.OnFailureFromServer(cause);
            }
        });
        request.setBody(obj.toString());
        request.makeJsonPostRequest(context, URL, null, TAG);
    }

    private void getData(@NonNull final Context context, @NonNull final Object obj, @NonNull String URL, @Nullable String query, @Nullable String TAG) {
        //if(query!=null){if(!query.equalsIgnoreCase("")){URL += "?"+query;}}
        VolleyServices request = new VolleyServices(context);
        request.setCallbacks(new VolleyServices.ResposeCallBack() {
            @Override
            public void OnSuccess(JSONObject arg0) {
                Log.d("Response", arg0.toString());
                parseResponse(context, obj, arg0);
            }

            @Override
            public void OnFailure(int cause) {
                mUiCallBack.OnFailureFromServer(context.getString(cause));
            }

            @Override
            public void OnFailure(String cause) {
                mUiCallBack.OnFailureFromServer(cause);
            }
        });
        request.setBody(obj.toString());
        request.makeJsonGETRequest(context, URL, query, TAG);
    }

    private void parseResponse(Context context, Object obj, JSONObject resp) {
        Log.d(TAG, resp.toString());
        if (obj instanceof IndexModel) {
            IndexModel model = new IndexModel();
            if (model.toObject(resp.toString())) {
                mUiCallBack.OnSuccessFromServer(model);
            } else {
                ProfileMainModel profileModel = new ProfileMainModel();
                if (profileModel.toObject(resp.toString())) {
                    mUiCallBack.OnSuccessFromServer(profileModel);
                } else {
                    mUiCallBack.OnError(context.getString(R.string.ErrorResponseData));
                }
            }

        } else if (obj instanceof NotificationListModel) {
            NotificationListModel model = new NotificationListModel();
            if (model.toObject(resp.toString())) {
                mUiCallBack.OnSuccessFromServer(model);
            } else {
                mUiCallBack.OnError(context.getString(R.string.ErrorResponseData));
            }
        } else if (obj instanceof KeyValueListModel) {
            KeyValueListModel model = new KeyValueListModel();
            if (model.toObject(resp.toString())) {
                mUiCallBack.OnSuccessFromServer(model);
            } else {
                mUiCallBack.OnError(context.getString(R.string.ErrorResponseData));
            }
        } else if (obj instanceof HomeIndexModel) {
            HomeIndexModel model = new HomeIndexModel();
            if (model.toObject(resp.toString())) {
                mUiCallBack.OnSuccessFromServer(model);
            } else {
                mUiCallBack.OnError(context.getString(R.string.ErrorResponseData));
            }
        } else if (obj instanceof LoginModel) {
            StatusMainModel model = new StatusMainModel();
            if (model.toObject(resp.toString())) {
                mUiCallBack.OnSuccessFromServer(model);
            } else {
                    mUiCallBack.OnError(context.getString(R.string.ErrorResponseData));
            }
        }else if (obj instanceof RegisterModel ) {
            StatusProfileModel model = new StatusProfileModel();
            if (model.toObject(resp.toString())) {
                mUiCallBack.OnSuccessFromServer(model);
            } else {
                    mUiCallBack.OnError(context.getString(R.string.ErrorResponseData));
            }
        } else if (obj instanceof LocationCheckPostModel) {
            StatusModel model = new StatusModel();
            if (model.toObject(resp.toString())) {
                mUiCallBack.OnSuccessFromServer(model);
            } else {
                LocationCheckResponseModel responseModel = new LocationCheckResponseModel();
                if (responseModel.toObject(resp.toString())) {
                    mUiCallBack.OnSuccessFromServer(responseModel);
                } else {
                    mUiCallBack.OnError(context.getString(R.string.ErrorResponseData));
                }
            }
        } else if (obj instanceof MyCityListModel) {
            MyCityListModel model = new MyCityListModel();
            if (model.toObject(resp.toString())) {
                mUiCallBack.OnSuccessFromServer(model);
            } else {
                mUiCallBack.OnError(context.getString(R.string.ErrorResponseData));
            }
        }else if (obj instanceof CityListMainModel) {
            CityListMainModel model = new CityListMainModel();
            if (model.toObject(resp.toString())) {
                mUiCallBack.OnSuccessFromServer(model);
            } else {
                mUiCallBack.OnError(context.getString(R.string.ErrorResponseData));
            }
        } else if ( obj instanceof AddServiceModel || obj instanceof InsertPostModel || obj instanceof DeletePostModel || obj instanceof InsertGalleryModel  || obj instanceof TicketSubmitModel || obj instanceof PaymentPostModel) {
            StatusModel model = new StatusModel();
            if (model.toObject(resp.toString())) {
                mUiCallBack.OnSuccessFromServer(model);
            } else {
                mUiCallBack.OnError(context.getString(R.string.ErrorResponseData));
            }
        } /*else if (obj instanceof LatlongMainModel) {
            StatusMainModel statusMainModel = new StatusMainModel();
            if (statusMainModel.toObject(resp.toString())) {
                mUiCallBack.OnSuccessFromServer(statusMainModel);
            } else {
                mUiCallBack.OnError(context.getString(R.string.ErrorResponseData));
            }
        }*/else if (obj instanceof UpdateLanguageModel) {
            StatusModel model = new StatusModel();
            if (model.toObject(resp.toString())) {
                mUiCallBack.OnSuccessFromServer(model);
            } else {
                mUiCallBack.OnError(context.getString(R.string.ErrorResponseData));
            }
        } else if (obj instanceof LatlongMainModel) {
            LatlongMainModel model = new LatlongMainModel();
            if (model.toObject(resp.toString())) {
                mUiCallBack.OnSuccessFromServer(model);
            } else {
                mUiCallBack.OnError(context.getString(R.string.ErrorResponseData));
            }
        }else if (obj instanceof LatlongModel) {
            LatlongMainModel latlongModel = new LatlongMainModel();
            if (latlongModel.toObject(resp.toString())) {
                mUiCallBack.OnSuccessFromServer(latlongModel);
            } else {
                mUiCallBack.OnError(context.getString(R.string.ErrorResponseData));
            }
        }else if (obj instanceof SeeAllCategoryListMainModel) {
            SeeAllCategoryListMainModel model = new SeeAllCategoryListMainModel();
            if (model.toObject(resp.toString())) {
                mUiCallBack.OnSuccessFromServer(model);
            } else {
                mUiCallBack.OnError(context.getString(R.string.ErrorResponseData));
            }
        } else if (obj instanceof VendorModel) {
            VendorModel model = new VendorModel();
            if (model.toObject(resp.toString())) {
                mUiCallBack.OnSuccessFromServer(model);
            } else {
                mUiCallBack.OnError(context.getString(R.string.ErrorResponseData));
            }
        }
        else if (obj instanceof TokenPostModel) {
            LiveStreamTokenModel model = new LiveStreamTokenModel();
            if (model.toObject(resp.toString())) {
                mUiCallBack.OnSuccessFromServer(model);
            } else {
                mUiCallBack.OnError(context.getString(R.string.ErrorResponseData));
            }
        } else if (obj instanceof AudienceTokenPostModel) {
            LiveStreamTokenModel model = new LiveStreamTokenModel();
            if (model.toObject(resp.toString())) {
                mUiCallBack.OnSuccessFromServer(model);
            } else {
                mUiCallBack.OnError(context.getString(R.string.ErrorResponseData));
            }
        } else if (obj instanceof LanguageListModel) {
            LanguageListModel model = new LanguageListModel();
            if (model.toObject(resp.toString())) {
                mUiCallBack.OnSuccessFromServer(model);
            } else {
                mUiCallBack.OnError(context.getString(R.string.ErrorResponseData));
            }
        } else if (obj instanceof InsertUserPostModel) {
            StatusModel model = new StatusModel();
            if (model.toObject(resp.toString())) {
                mUiCallBack.OnSuccessFromServer(model);
            } else {
                mUiCallBack.OnError(context.getString(R.string.ErrorResponseData));
            }
        } else if (obj instanceof CategoryListModel) {
            CategoryListModel model = new CategoryListModel();
            if (model.toObject(resp.toString())) {
                mUiCallBack.OnSuccessFromServer(model);
            } else {
                mUiCallBack.OnError(context.getString(R.string.ErrorResponseData));
            }
        } else if (obj instanceof PostListModel) {
            PostListModel model = new PostListModel();
            if (model.toObject(resp.toString())) {
                mUiCallBack.OnSuccessFromServer(model);
            } else {
                mUiCallBack.OnError(context.getString(R.string.ErrorResponseData));
            }
        } else if (obj instanceof RatingModel) {
            StatusModel model = new StatusModel();
            if (model.toObject(resp.toString())) {
                mUiCallBack.OnSuccessFromServer(model);
            } else {
                mUiCallBack.OnError(context.getString(R.string.ErrorResponseData));
            }
        }/*else if (obj instanceof LatlongModel) {
            StatusMainModel statusMainModel = new StatusMainModel();
            if (statusMainModel.toObject(resp.toString())) {
                mUiCallBack.OnSuccessFromServer(statusMainModel);
            } else {
                mUiCallBack.OnError(context.getString(R.string.ErrorResponseData));
            }
        }*/else if (obj instanceof HomePageModel) {
            HomePageModel model = new HomePageModel();
            if (model.toObject(resp.toString())) {
                mUiCallBack.OnSuccessFromServer(model);
            } else {
                mUiCallBack.OnError(context.getString(R.string.ErrorResponseData));
            }
        }else if (obj instanceof HomePageMainModel) {
            HomePageMainModel model = new HomePageMainModel();
            if (model.toObject(resp.toString())) {
                mUiCallBack.OnSuccessFromServer(model);
            } else {
                mUiCallBack.OnError(context.getString(R.string.ErrorResponseData));
            }
        }else if (obj instanceof VendorDetailsMainModel) {
            VendorDetailsMainModel model = new VendorDetailsMainModel();
            if (model.toObject(resp.toString())) {
                mUiCallBack.OnSuccessFromServer(model);
            } else {
                mUiCallBack.OnError(context.getString(R.string.ErrorResponseData));
            }
        }else if (obj instanceof SubCatMainModel) {
            SubCatMainModel model = new SubCatMainModel();
            if (model.toObject(resp.toString())) {
                mUiCallBack.OnSuccessFromServer(model);
            } else {
                mUiCallBack.OnError(context.getString(R.string.ErrorResponseData));
            }
        } else if (obj instanceof AgeListModel) {
            AgeListModel model = new AgeListModel();
            if (model.toObject(resp.toString())) {
                mUiCallBack.OnSuccessFromServer(model);
            } else {
                mUiCallBack.OnError(context.getString(R.string.ErrorResponseData));
            }
        } else if (obj instanceof PackageListModel) {
            PackageListModel model = new PackageListModel();
            if (model.toObject(resp.toString())) {
                mUiCallBack.OnSuccessFromServer(model);
            } else {
                mUiCallBack.OnError(context.getString(R.string.ErrorResponseData));
            }
        } else if (obj instanceof SectionListModel) {
            SectionListModel model = new SectionListModel();
            if (model.toObject(resp.toString())) {
                mUiCallBack.OnSuccessFromServer(model);
            } else {
                mUiCallBack.OnError(context.getString(R.string.ErrorResponseData));
            }
        }else if (obj instanceof AdsListModel) {
            AdsListModel model = new AdsListModel();
            if (model.toObject(resp.toString())) {
                mUiCallBack.OnSuccessFromServer(model);
            } else {
                mUiCallBack.OnError(context.getString(R.string.ErrorResponseData));
            }
        }   else if (obj instanceof HomeSubCategoryListModel) {
            HomeSubCategoryListModel model = new HomeSubCategoryListModel();
            if (model.toObject(resp.toString())) {
                mUiCallBack.OnSuccessFromServer(model);
            } else {
                mUiCallBack.OnError(context.getString(R.string.ErrorResponseData));
            }
        }else if (obj instanceof KeyValueMainModel) {
            KeyValueMainModel model = new KeyValueMainModel();
            if (model.toObject(resp.toString())) {
                mUiCallBack.OnSuccessFromServer(model);
            } else {
                mUiCallBack.OnError(context.getString(R.string.ErrorResponseData));
            }
        } else if (obj instanceof UtilityModel) {
            UtilityModel model = new UtilityModel();
            if (model.toObject(resp.toString())) {
                mUiCallBack.OnSuccessFromServer(model);
            } else {
                mUiCallBack.OnError(context.getString(R.string.ErrorResponseData));
            }
        } else if (obj instanceof WishListModel) {
            WishListModel model = new WishListModel();
            if (model.toObject(resp.toString())) {
                mUiCallBack.OnSuccessFromServer(model);
            } else {
                mUiCallBack.OnError(context.getString(R.string.ErrorResponseData));
            }
        } else if (obj instanceof AdvertiseModel) {
            AdvertiseModel model = new AdvertiseModel();
            if (model.toObject(resp.toString())) {
                mUiCallBack.OnSuccessFromServer(model);
            } else {
                mUiCallBack.OnError(context.getString(R.string.ErrorResponseData));
            }
        } else if (obj instanceof ProfileModel) {
            StatusResponseModel profileModel = new StatusResponseModel();
            if (profileModel.toObject(resp.toString())) {
                mUiCallBack.OnSuccessFromServer(profileModel);
            } else {
                mUiCallBack.OnError(context.getString(R.string.ErrorResponseData));
            }
        }else if (obj instanceof ProfileMainModel) {
            ProfileMainModel profileModel = new ProfileMainModel();
            if (profileModel.toObject(resp.toString())) {
                mUiCallBack.OnSuccessFromServer(profileModel);
            } else {
                mUiCallBack.OnError(context.getString(R.string.ErrorResponseData));
            }
        } else if (obj instanceof StatusModel || obj instanceof ChangePasswordModel || obj instanceof PushNotificationModel) {
            StatusModel model = new StatusModel();
            if (model.toObject(resp.toString())) {
                mUiCallBack.OnSuccessFromServer(model);
            } else {
                ProfileMainModel profileMode = new ProfileMainModel();
                if (profileMode.toObject(resp.toString())) {
                    mUiCallBack.OnSuccessFromServer(profileMode);
                } else {
                    mUiCallBack.OnError(context.getString(R.string.ErrorResponseData));
                }
            }
        } else if (obj instanceof OrderDetailMainModel) {
            StatusModel model = new StatusModel();
            if (model.toObject(resp.toString())) {
                mUiCallBack.OnSuccessFromServer(model);
            } else {
                OrderDetailMainModel orderDetailMainModel = new OrderDetailMainModel();
                if (orderDetailMainModel.toObject(resp.toString())) {
                    mUiCallBack.OnSuccessFromServer(orderDetailMainModel);
                } else {
                    mUiCallBack.OnError(context.getString(R.string.ErrorResponseData));
                }
            }
        } else if (obj instanceof CountryListModel) {
            CountryListModel model = new CountryListModel();
            if (model.toObject(resp.toString())) {
                mUiCallBack.OnSuccessFromServer(model);
            } else {
                mUiCallBack.OnError(context.getString(R.string.ErrorResponseData));
            }
        } else if (obj instanceof WishlistPostModel) {
            StatusModel model = new StatusModel();
            if (model.toObject(resp.toString())) {
                mUiCallBack.OnSuccessFromServer(model);
            } else {
                mUiCallBack.OnError(context.getString(R.string.ErrorResponseData));
            }
        } else if (obj instanceof WishListMainModel) {
            WishListMainModel model = new WishListMainModel();
            if (model.toObject(resp.toString())) {
                mUiCallBack.OnSuccessFromServer(model);
            } else {
                mUiCallBack.OnError(context.getString(R.string.ErrorResponseData));
            }
        } else if (obj instanceof OrderStatusUpdateModel) {
            StatusModel model = new StatusModel();
            if (model.toObject(resp.toString())) {
                mUiCallBack.OnSuccessFromServer(model);
            } else {
                mUiCallBack.OnError(context.getString(R.string.ErrorResponseData));
            }
        } else if (obj instanceof FixingTimePostModel) {
            StatusModel model = new StatusModel();
            if (model.toObject(resp.toString())) {
                mUiCallBack.OnSuccessFromServer(model);
            } else {
                mUiCallBack.OnError(context.getString(R.string.ErrorResponseData));
            }
        }  else if (obj instanceof ForgotPasswordModel || obj instanceof WalletAmountCreditModel || obj instanceof ContactPostModel) {
            StatusModel model = new StatusModel();
            if (model.toObject(resp.toString())) {
                mUiCallBack.OnSuccessFromServer(model);
            } else {
                mUiCallBack.OnError(context.getString(R.string.ErrorResponseData));
            }
        } else if (obj instanceof LocationUpdateModel) {
            StatusModel model = new StatusModel();
            if (model.toObject(resp.toString())) {
                mUiCallBack.OnSuccessFromServer(model);
            } else {
                mUiCallBack.OnError(context.getString(R.string.ErrorResponseData));
            }
        } else if (obj instanceof OrderListMainModel) {
            OrderListMainModel model = new OrderListMainModel();
            if (model.toObject(resp.toString())) {
                mUiCallBack.OnSuccessFromServer(model);
            } else {
                mUiCallBack.OnError(context.getString(R.string.ErrorResponseData));
            }
        }else if (obj instanceof InsertAccountModel) {
            InsertAccountModel model = new InsertAccountModel();
            if (model.toObject(resp.toString())) {
                mUiCallBack.OnSuccessFromServer(model);
            } else {
                mUiCallBack.OnError(context.getString(R.string.ErrorResponseData));
            }
        }else if (obj instanceof InsertAccountModel) {
            StatusMainModel model = new StatusMainModel();
            if (model.toObject(resp.toString())) {
                mUiCallBack.OnSuccessFromServer(model);
            } else {
                mUiCallBack.OnError(context.getString(R.string.ErrorResponseData));
            }
        }
        else if (obj instanceof InsertReviewModel) {
            InsertReviewModel model = new InsertReviewModel();
            if (model.toObject(resp.toString())) {
                mUiCallBack.OnSuccessFromServer(model);
            } else {
                mUiCallBack.OnError(context.getString(R.string.ErrorResponseData));
            }
        }else if (obj instanceof InsertReviewModel) {
            StatusMainModel model = new StatusMainModel();
            if (model.toObject(resp.toString())) {
                mUiCallBack.OnSuccessFromServer(model);
            } else {
                mUiCallBack.OnError(context.getString(R.string.ErrorResponseData));
            }
        }
        else if (obj instanceof InsertRecentCouponModel) {
            InsertRecentCouponModel model = new InsertRecentCouponModel();
            if (model.toObject(resp.toString())) {
                mUiCallBack.OnSuccessFromServer(model);
            } else {
                mUiCallBack.OnError(context.getString(R.string.ErrorResponseData));
            }
        }else if (obj instanceof InsertRecentCouponModel) {
            StatusMainModel model = new StatusMainModel();
            if (model.toObject(resp.toString())) {
                mUiCallBack.OnSuccessFromServer(model);
            } else {
                mUiCallBack.OnError(context.getString(R.string.ErrorResponseData));
            }
        }
        else if (obj instanceof SubCategoryListModel) {
            SubCategoryListModel model = new SubCategoryListModel();
            if (model.toObject(resp.toString())) {
                mUiCallBack.OnSuccessFromServer(model);
            } else {
                mUiCallBack.OnError(context.getString(R.string.ErrorResponseData));
            }
        } else if (obj instanceof ProductPostModel) {
            ProductListMainModel model = new ProductListMainModel();
            if (model.toObject(resp.toString())) {
                mUiCallBack.OnSuccessFromServer(model);
            } else {
                mUiCallBack.OnError(context.getString(R.string.ErrorResponseData));
            }
        } else if (obj instanceof FilterMainModel) {
            FilterMainModel model = new FilterMainModel();
            if (model.toObject(resp.toString())) {
                mUiCallBack.OnSuccessFromServer(model);
            } else {
                mUiCallBack.OnError(context.getString(R.string.ErrorResponseData));
            }
        } else if (obj instanceof ProductDetailModel) {
            ProductDetailModel model = new ProductDetailModel();
            if (model.toObject(resp.toString())) {
                mUiCallBack.OnSuccessFromServer(model);
            } else {
                mUiCallBack.OnError(context.getString(R.string.ErrorResponseData));
            }
        } else if (obj instanceof VendorDetailsMainModel) {
            VendorDetailsMainModel model = new VendorDetailsMainModel();
            if (model.toObject(resp.toString())) {
                mUiCallBack.OnSuccessFromServer(model);
            } else {
                mUiCallBack.OnError(context.getString(R.string.ErrorResponseData));
            }
        }
        else if (obj instanceof VendorStoreMainModel) {
            VendorStoreMainModel model = new VendorStoreMainModel();
            if (model.toObject(resp.toString())) {
                mUiCallBack.OnSuccessFromServer(model);
            } else {
                mUiCallBack.OnError(context.getString(R.string.ErrorResponseData));
            }
        } else if (obj instanceof OfferModel) {
            OfferModel offerModel = new OfferModel();
            if (offerModel.toObject(resp.toString())) {
                mUiCallBack.OnSuccessFromServer(offerModel);
            } else {
                mUiCallBack.OnError(context.getString(R.string.ErrorResponseData));
            }
        }else if (obj instanceof ReviewModel) {
            ReviewModel reviewModel = new ReviewModel();
            if (reviewModel.toObject(resp.toString())) {
                mUiCallBack.OnSuccessFromServer(reviewModel);
            } else {
                mUiCallBack.OnError(context.getString(R.string.ErrorResponseData));
            }
        }else if (obj instanceof AccountMainModel) {
            AccountMainModel accountMainModel = new AccountMainModel();
            if (accountMainModel.toObject(resp.toString())) {
                mUiCallBack.OnSuccessFromServer(accountMainModel);
            } else {
                mUiCallBack.OnError(context.getString(R.string.ErrorResponseData));
            }
        }else if (obj instanceof MembershipMainModel) {
            MembershipMainModel membershipMainModel = new MembershipMainModel();
            if (membershipMainModel.toObject(resp.toString())) {
                mUiCallBack.OnSuccessFromServer(membershipMainModel);
            } else {
                mUiCallBack.OnError(context.getString(R.string.ErrorResponseData));
            }
        }
        else if (obj instanceof MembershipModel) {
            MembershipMainModel model = new MembershipMainModel();
            if (model.toObject(resp.toString())) {
                mUiCallBack.OnSuccessFromServer(model);
            } else {
                MembershipModel membershipModel = new MembershipModel();
                if (membershipModel.toObject(resp.toString())) {
                    mUiCallBack.OnSuccessFromServer(membershipModel);
                } else {
                    mUiCallBack.OnError(context.getString(R.string.ErrorResponseData));
                }
            }
        }
        else if (obj instanceof RecentCouponMainModel) {
            RecentCouponMainModel recentCouponMainModel = new RecentCouponMainModel();
            if (recentCouponMainModel.toObject(resp.toString())) {
                mUiCallBack.OnSuccessFromServer(recentCouponMainModel);
            } else {
                mUiCallBack.OnError(context.getString(R.string.ErrorResponseData));
            }
        }
        else if (obj instanceof RecentCouponModel) {
            RecentCouponMainModel model = new RecentCouponMainModel();
            if (model.toObject(resp.toString())) {
                mUiCallBack.OnSuccessFromServer(model);
            } else {
                RecentCouponModel accountModel = new RecentCouponModel();
                if (accountModel.toObject(resp.toString())) {
                    mUiCallBack.OnSuccessFromServer(accountModel);
                } else {
                    mUiCallBack.OnError(context.getString(R.string.ErrorResponseData));
                }
            }
        }
        else if (obj instanceof AccountModel) {
            AccountMainModel model = new AccountMainModel();
            if (model.toObject(resp.toString())) {
                mUiCallBack.OnSuccessFromServer(model);
            } else {
                AccountModel accountModel = new AccountModel();
                if (accountModel.toObject(resp.toString())) {
                    mUiCallBack.OnSuccessFromServer(accountModel);
                } else {
                    mUiCallBack.OnError(context.getString(R.string.ErrorResponseData));
                }
            }
        }else if (obj instanceof CommentListMainModel) {
            CommentListMainModel model = new CommentListMainModel();
            if (model.toObject(resp.toString())) {
                mUiCallBack.OnSuccessFromServer(model);
            } else {
                mUiCallBack.OnError(context.getString(R.string.ErrorResponseData));
            }
        }else if (obj instanceof ProductDetailMainModel) {
            ProductDetailMainModel model = new ProductDetailMainModel();
            if (model.toObject(resp.toString())) {
                mUiCallBack.OnSuccessFromServer(model);
            } else {
                mUiCallBack.OnError(context.getString(R.string.ErrorResponseData));
            }
        } else if (obj instanceof OrderListMainModel) {
            OrderListMainModel model = new OrderListMainModel();
            if (model.toObject(resp.toString())) {
                mUiCallBack.OnSuccessFromServer(model);
            } else {
                mUiCallBack.OnError(context.getString(R.string.ErrorResponseData));
            }
        } else if (obj instanceof TicketsListModel) {
            TicketsListModel model = new TicketsListModel();
            if (model.toObject(resp.toString())) {
                mUiCallBack.OnSuccessFromServer(model);
            } else {
                mUiCallBack.OnError(context.getString(R.string.ErrorResponseData));
            }
        } else if (obj instanceof CartMainModel || obj instanceof UpdateCartPostModel) {
            CartMainModel model = new CartMainModel();
            if (model.toObject(resp.toString())) {
                mUiCallBack.OnSuccessFromServer(model);
            } else {
                mUiCallBack.OnError(context.getString(R.string.ErrorResponseData));
            }
        } else if (obj instanceof CouponCodePostModel) {
            StatusResponseModel model = new StatusResponseModel();
            if (model.toObject(resp.toString())) {
                mUiCallBack.OnSuccessFromServer(model);
            } else {
                CartMainModel addedPartsListModel = new CartMainModel();
                if (addedPartsListModel.toObject(resp.toString())) {
                    mUiCallBack.OnSuccessFromServer(addedPartsListModel);
                } else {
                    mUiCallBack.OnError(context.getString(R.string.ErrorResponseData));
                }
            }
        }  else if (obj instanceof UserAdsListModel) {
            UserAdsListModel model = new UserAdsListModel();
            if (model.toObject(resp.toString())) {
                mUiCallBack.OnSuccessFromServer(model);
            } else {
                mUiCallBack.OnError(context.getString(R.string.ErrorResponseData));
            }
        }  else if (obj instanceof PostCommentModel) {
            StatusModel model = new StatusModel();
            if (model.toObject(resp.toString())) {
                mUiCallBack.OnSuccessFromServer(model);
            } else {
                mUiCallBack.OnError(context.getString(R.string.ErrorResponseData));
            }
        } else if (obj instanceof UtilityMainModel) {
            UtilityMainModel model = new UtilityMainModel();
            if (model.toObject(resp.toString())) {
                mUiCallBack.OnSuccessFromServer(model);
            } else {
                mUiCallBack.OnError(context.getString(R.string.ErrorResponseData));
            }
        } else if (obj instanceof PostCommentsListModel) {
            PostCommentsListModel model = new PostCommentsListModel();
            if (model.toObject(resp.toString())) {
                mUiCallBack.OnSuccessFromServer(model);
            } else {
                mUiCallBack.OnError(context.getString(R.string.ErrorResponseData));
            }
        } else if (obj instanceof PostModel) {
            PostModel model = new PostModel();
            if (model.toObject(resp.toString())) {
                mUiCallBack.OnSuccessFromServer(model);
            } else {
                mUiCallBack.OnError(context.getString(R.string.ErrorResponseData));
            }
        } else if (obj instanceof RelatedProductsListModel) {
            RelatedProductsListModel model = new RelatedProductsListModel();
            if (model.toObject(resp.toString())) {
                mUiCallBack.OnSuccessFromServer(model);
            } else {
                mUiCallBack.OnError(context.getString(R.string.ErrorResponseData));
            }
        } else if (obj instanceof UserAddressModel) {
            StatusResponseModel statusModel = new StatusResponseModel();
            if (statusModel.toObject(resp.toString())) {
                mUiCallBack.OnSuccessFromServer(statusModel);
            } else {
                mUiCallBack.OnError(context.getString(R.string.ErrorResponseData));
            }
        } else if (obj instanceof OrderSubmitModel) {
            StatusMainModel statusModel = new StatusMainModel();
            if (statusModel.toObject(resp.toString())) {
                mUiCallBack.OnSuccessFromServer(statusModel);
            } else {
                mUiCallBack.OnError(context.getString(R.string.ErrorResponseData));
            }
        }else if (obj instanceof OrderDetailsMainModel) {
            OrderDetailsMainModel statusModel = new OrderDetailsMainModel();
            if (statusModel.toObject(resp.toString())) {
                mUiCallBack.OnSuccessFromServer(statusModel);
            } else {
                mUiCallBack.OnError(context.getString(R.string.ErrorResponseData));
            }
        }else if (obj instanceof SearchModel) {
            SearchMainModel model = new SearchMainModel();
            if (model.toObject(resp.toString())) {
                mUiCallBack.OnSuccessFromServer(model);
            } else {
                mUiCallBack.OnError(context.getString(R.string.ErrorResponseData));
            }
        } else if (obj instanceof UserAddressListModel) {
            UserAddressListModel statusModel = new UserAddressListModel();
            if (statusModel.toObject(resp.toString())) {
                mUiCallBack.OnSuccessFromServer(statusModel);
            } else {
                mUiCallBack.OnError(context.getString(R.string.ErrorResponseData));
            }
        }else if (obj instanceof LocationModel) {
            LocationModel locationModel = new LocationModel();
            if (locationModel.toObject(resp.toString())) {
                mUiCallBack.OnSuccessFromServer(locationModel);
            } else {
                mUiCallBack.OnError(context.getString(R.string.ErrorResponseData));
            }
        }else if (obj instanceof CategoryModel) {
            CategoryModel categoryModel = new CategoryModel();
            if (categoryModel.toObject(resp.toString())) {
                mUiCallBack.OnSuccessFromServer(categoryModel);
            } else {
                mUiCallBack.OnError(context.getString(R.string.ErrorResponseData));
            }
        }else if (obj instanceof NearbyModel) {
            NearbyModel nearbyModel = new NearbyModel();
            if (nearbyModel.toObject(resp.toString())) {
                mUiCallBack.OnSuccessFromServer(nearbyModel);
            } else {
                mUiCallBack.OnError(context.getString(R.string.ErrorResponseData));
            }
        } else if (obj instanceof CartPostModel) {
            StatusMainModel model = new StatusMainModel();
            if (model.toObject(resp.toString())) {
                mUiCallBack.OnSuccessFromServer(model);
            } else {
                mUiCallBack.OnError(context.getString(R.string.ErrorResponseData));
            }
        }  else if (obj instanceof StatusMainModel) {
            StatusMainModel model = new StatusMainModel();
            if (model.toObject(resp.toString())) {
                mUiCallBack.OnSuccessFromServer(model);
            } else {
                mUiCallBack.OnError(context.getString(R.string.ErrorResponseData));
            }
        } else if (obj instanceof WishlistPostModel) {
            StatusMainModel model = new StatusMainModel();
            if (model.toObject(resp.toString())) {
                mUiCallBack.OnSuccessFromServer(model);
            } else {
                mUiCallBack.OnError(context.getString(R.string.ErrorResponseData));
            }
        } else if (obj instanceof WalletMainModel) {
            WalletMainModel model = new WalletMainModel();
            if (model.toObject(resp.toString())) {
                mUiCallBack.OnSuccessFromServer(model);
            } else {
                mUiCallBack.OnError(context.getString(R.string.ErrorResponseData));
            }
        } else if (obj instanceof FeedbackMainModel) {
            FeedbackMainModel model = new FeedbackMainModel();
            if (model.toObject(resp.toString())) {
                mUiCallBack.OnSuccessFromServer(model);
            } else {
                mUiCallBack.OnError(context.getString(R.string.ErrorResponseData));
            }
        } else if (obj instanceof OrderMainModel) {
            OrderMainModel model = new OrderMainModel();
            if (model.toObject(resp.toString())) {
                mUiCallBack.OnSuccessFromServer(model);
            } else {
                mUiCallBack.OnError(context.getString(R.string.ErrorResponseData));
            }
        } else if (obj instanceof TicketsDetailModel) {
            TicketsDetailModel model = new TicketsDetailModel();
            if (model.toObject(resp.toString())) {
                mUiCallBack.OnSuccessFromServer(model);
            } else {
                mUiCallBack.OnError(context.getString(R.string.ErrorResponseData));
            }
        } else if (obj instanceof BankDetailsModel) {
            StatusModel model = new StatusModel();
            if (model.toObject(resp.toString())) {
                mUiCallBack.OnSuccessFromServer(model);
            } else {
                mUiCallBack.OnError(context.getString(R.string.ErrorResponseData));
            }
        } else if (obj instanceof FeedbackPostModel) {
            StatusModel model = new StatusModel();
            if (model.toObject(resp.toString())) {
                mUiCallBack.OnSuccessFromServer(model);
            } else {
                mUiCallBack.OnError(context.getString(R.string.ErrorResponseData));
            }
        } else if (obj instanceof AddressModel) {
            AddressListModel listModel = new AddressListModel();
            if (listModel.toObject(resp.toString())) {
                mUiCallBack.OnSuccessFromServer(listModel);
            } else {
                AddressModel model = new AddressModel();
                if (model.toObject(resp.toString())) {
                    mUiCallBack.OnSuccessFromServer(model);
                } else {
                    StatusModel statusModel = new StatusModel();
                    if (statusModel.toObject(resp.toString())) {
                        mUiCallBack.OnSuccessFromServer(statusModel);
                    } else {
                        mUiCallBack.OnError(context.getString(R.string.ErrorResponseData));
                    }
                }

            }
        } else if (obj instanceof AddressListModel) {
            AddressListModel listModel = new AddressListModel();
            if (listModel.toObject(resp.toString())) {
                mUiCallBack.OnSuccessFromServer(listModel);
            } else {
                mUiCallBack.OnError(context.getString(R.string.ErrorResponseData));
            }
        } else if (obj instanceof LocationApiModel) {
            LocationApiModel model = new LocationApiModel();
            if (model.toObject(resp.toString())) {
                mUiCallBack.OnSuccessFromServer(model);
            } else {
                StatusModel statusModel = new StatusModel();
                if (statusModel.toObject(resp.toString())) {
                    mUiCallBack.OnSuccessFromServer(statusModel);
                } else {
                    mUiCallBack.OnError(context.getString(R.string.ErrorResponseData));
                }
            }
        } else if (obj instanceof IndexModel) {
            IndexModel model = new IndexModel();
            if (model.toObject(resp.toString())) {
                mUiCallBack.OnSuccessFromServer(model);
            } else {
                StatusModel statusModel = new StatusModel();
                if (statusModel.toObject(resp.toString())) {
                    mUiCallBack.OnSuccessFromServer(statusModel);
                } else {
                    mUiCallBack.OnError(context.getString(R.string.ErrorResponseData));
                }
            }
        }
    }

    public void getwallet(Context context,@NonNull int type, @NonNull int index, @NonNull int size, ServerResponseInterface mCallInterface, String TAG) {
        setCallbacks(mCallInterface);
        String query = null;
        query = query!=null? query+"type="+type : "type="+type;
        query = query!=null? query+"&index="+index : "&index="+index;
        query = query!=null? query+"&size="+size : "size="+size;
       // getData(context,new WalletListModel(), ServerConstants.URL_WalletHistory, query, TAG);
    }

    public void getMaterialList(Context context, ServerResponseInterface mCallInterface, String TAG) {
        setCallbacks(mCallInterface);
        String query = null;
        //getData(context, new KeyValueMainModel(), ServerConstants.URL_GetMaterialList, query, TAG);
    }

    public void getSubCatList(Context context, String catId, ServerResponseInterface mCallInterface, String TAG) {
        setCallbacks(mCallInterface);
        String query = null;
        query = query != null ? query + "&id_parent=" + catId : "id_parent=" + catId;
       // getData(context, new SubCatMainModel(), ServerConstants.URL_GetSubCategoryList, query, TAG);
    }

    public void getGuestUserCreation(Context context, ServerResponseInterface mCallInterface, String TAG){
        setCallbacks(mCallInterface);
        HomeIndexModel model = new HomeIndexModel();
        model.setSystem_info(globalFunctions.getDevice());
        model.setUid(globalFunctions.getUniqueID(context));
        postData(context, model, ServerConstants.URL_GuestUserCreation, null, TAG, true);
    }

    public void loginUser(Context context, LoginModel loginModel, ServerResponseInterface mCallInterface, String TAG) {
        setCallbacks(mCallInterface);
        postData(context, loginModel, ServerConstants.URL_LoginUser, TAG);
    }

    public void registerUser(Context context, RegisterModel registerModel, ServerResponseInterface mCallInterface, String TAG) {
        setCallbacks(mCallInterface);
        registerModel.setUid(GlobalFunctions.getUniqueID(context));
        registerModel.setSystemInfo(GlobalFunctions.getDevice());
        postData(context, registerModel, ServerConstants.URL_RegisterUser, TAG);
    }

    public void checkMobileNumber(Context context, LoginModel loginModel, ServerResponseInterface mCallInterface, String TAG) {
        setCallbacks(mCallInterface);
        String query = null;
        String url = ServerConstants.URL_CheckMobile;
        postData(context, loginModel, url, query, TAG);
    }

    public void getCheckWishList(Context context, String  master_id, ServerResponseInterface mCallInterface, String TAG) {
        setCallbacks(mCallInterface);
        String query = null;

        query = query != null ? query + "&master_id=" + master_id: "master_id=" + master_id;


        getData(context, new StatusMainModel(), ServerConstants.URL_Check_wishlist, query, TAG);
    }

    public void logout(Context context, UpdateLanguageModel updateLanguageModel, ServerResponseInterface mCallInterface, String TAG) {
        setCallbacks(mCallInterface);
        postData(context, updateLanguageModel, ServerConstants.URL_LogoutUser, null, TAG);
    }

    public void updateUser(Context context, ProfileModel profileModel, ServerResponseInterface mCallInterface, String TAG) {
        setCallbacks(mCallInterface);
        String url = ServerConstants.URL_UpdateProfile;
        postData(context, profileModel, url, TAG);
    }

    public void getProfile(Context context, ServerResponseInterface mCallInterface, String TAG) {
        setCallbacks(mCallInterface);
        String query = null;
        String url = ServerConstants.URL_GetProfile;
        getData(context, new ProfileMainModel(), url, query, TAG);
    }

    public void sendPushNotificationID(Context context, PushNotificationModel pushNotificationModel, ServerResponseInterface mCallInterface, String TAG) {
        setCallbacks(mCallInterface);
        String query = null;
        //pushNotificationModel.setuii(GlobalFunctions.getUniqueID(context));
        //pushNotificationModel.setSystemInfo(GlobalFunctions.getDevice());
        postData(context, pushNotificationModel, ServerConstants.URL_PushNotification, query, TAG);
    }

    public void forgotMyPassword(Context context, ForgotPasswordModel forgotPasswordModel, ServerResponseInterface mCallInterface, String TAG) {
        setCallbacks(mCallInterface);
       // postData(context, forgotPasswordModel, ServerConstants.URL_ForgotMyPassword, TAG);
    }

    public void getCategoryList(Context context, ServerResponseInterface mCallInterface, String TAG) {
        setCallbacks(mCallInterface);
        String query = null;
       // query = query != null ? query + "&id_material_type=" + id : "id_material_type=" + id;
        getData(context, new HomePageMainModel(), ServerConstants.URL_Homepage, query, TAG);
    }
    public void updateLatLong(Context context,String latitude,String longitude, ServerResponseInterface mCallInterface, String TAG) {
        setCallbacks(mCallInterface);
        String query = null;
        query = query != null ? query + "&latitude=" + latitude : "latitude=" + latitude;
        query = query != null ? query + "&longitude=" + longitude : "longitude=" + longitude;

        String URL = ServerConstants.URL_LatlongUpdate;
        getData(context,new StatusMainModel(), URL, query, TAG);
    }
    public void getSeeAllCategoryList(Context context, ServerResponseInterface mCallInterface, String TAG) {
        setCallbacks(mCallInterface);
        String query = null;
        // query = query != null ? query + "&account=" + account : "account=" + account;
        getData(context, new SeeAllCategoryListMainModel(), ServerConstants.URL_GetCategoryList, query, TAG);
    }
    public void getNearbyList(Context context, ServerResponseInterface mCallInterface, String TAG) {
        setCallbacks(mCallInterface);
        String query = null;
        // query = query != null ? query + "&id_material_type=" + id : "id_material_type=" + id;
        getData(context, new HomePageMainModel(), ServerConstants.URL_Homepage, query, TAG);
    }
    public void getTrendingList(Context context, ServerResponseInterface mCallInterface, String TAG) {
        setCallbacks(mCallInterface);
        String query = null;
        // query = query != null ? query + "&id_material_type=" + id : "id_material_type=" + id;
        getData(context, new HomePageMainModel(), ServerConstants.URL_Homepage, query, TAG);
    }
    public void getMenu(Context context, ServerResponseInterface mCallInterface, String TAG) {
        setCallbacks(mCallInterface);
        String query = null;
       // getData(context, new UtilityMainModel(), ServerConstants.URL_GetMenu, query, TAG);
    }
    public void getAccountList(Context context, ServerResponseInterface mCallInterface, String TAG) {
        setCallbacks(mCallInterface);
        String query = null;

        getData(context, new AccountMainModel(), ServerConstants.URL_GetAccountList, query, TAG);
    }
    public void checkRedeemCode(Context context, String store_id, String redeem_code,String offer_store_id, ServerResponseInterface mCallInterface, String TAG) {
        setCallbacks(mCallInterface);
        String query = null;
        query = query != null ? query + "&store_id=" + store_id : "store_id=" + store_id;
        query = query != null ? query + "&redeem_code=" + redeem_code : "redeem_code=" + redeem_code;
        query = query != null ? query + "&offer_store_id=" + offer_store_id : "offer_store_id=" + offer_store_id;
        getData(context, new StatusMainModel(), ServerConstants.URL_check_redeme_code, query, TAG);
    }

    public void getHomeData(Context context, ServerResponseInterface mCallInterface, String TAG) {
        setCallbacks(mCallInterface);
        String query = null;
        getData(context, new HomePageMainModel(), ServerConstants.URL_Homepage, query, TAG);
    }

    public void getProductList(Context context, ProductPostModel productPostModel, ServerResponseInterface mCallInterface, String TAG) {
        setCallbacks(mCallInterface);
        //postData(context, productPostModel, ServerConstants.URL_ProductList, TAG);
    }

    public void getFilterList(Context context, String categoryId,String subCategoryId, ServerResponseInterface mCallInterface, String TAG) {
        setCallbacks(mCallInterface);
        String query = null;
//        query = query != null ? query + "&category_id=" + categoryId : "category_id=" + categoryId;
        query = query != null ? query + "&sub_category_id=" + subCategoryId : "sub_category_id=" + subCategoryId;
        //getData(context, new FilterMainModel(), ServerConstants.URL_FilterList, query, TAG);
    }

    public void getProductDetails(Context context, String productId, String variationId, ServerResponseInterface mCallInterface, String TAG) {
        setCallbacks(mCallInterface);
        String query = null;
        query = query != null ? query + "&id_product=" + productId : "id_product=" + productId;
        query = query != null ? query + "&id_variation=" + variationId : "id_variation=" + variationId;
        //getData(context, new ProductDetailMainModel(), ServerConstants.URL_ProductDetail, query, TAG);
    }

    public void getVendorDetails(Context context, String id, ServerResponseInterface mCallInterface, String TAG) {
        setCallbacks(mCallInterface);
        String query = null;
        query = query != null ? query + "&id=" + id : "id=" + id;
        getData(context, new VendorDetailsMainModel(), ServerConstants.URL_Vendorlist, query, TAG);
    }
    public void getVendorStoreList(Context context, String vendor_id,String sort,String category_id, ServerResponseInterface mCallInterface, String TAG) {
        setCallbacks(mCallInterface);
        String query = null;
        if (GlobalFunctions.isNotNullValue(vendor_id)){
            query = query != null ? query + "&vendor_id=" + vendor_id : "vendor_id=" + vendor_id;

        }else  if (GlobalFunctions.isNotNullValue(sort)){
                query = query != null ? query + "&sort=" + sort : "sort=" + sort;

        }else if (GlobalFunctions.isNotNullValue(category_id)){
            query = query != null ? query + "&category_id=" + category_id : "category_id=" + category_id;
        }

        getData(context, new VendorStoreMainModel(), ServerConstants.URL_Vendor_Store_list, query, TAG);
    }


    public void getWishListes(Context context, ServerResponseInterface mCallInterface, String TAG) {
        setCallbacks(mCallInterface);
        String query = null;

        getData(context, new WishListMainModel(), ServerConstants.URL_Wishlist, query, TAG);
    }

    public void getVendorRating(Context context, String vendorId,String size,String index, ServerResponseInterface mCallInterface, String TAG) {
        setCallbacks(mCallInterface);
        String query = null;
        query = query != null ? query + "&id_vendor=" + vendorId : "id_vendor=" + vendorId;
        query = query != null ? query + "&index=" + index : "index=" + index;
        query = query != null ? query + "&size=" + size : "size=" + size;
       // getData(context, new CommentListMainModel(), ServerConstants.URL_VendorRating, query, TAG);
    }
    public void insertUser(Context context,InsertAccountModel insertAccountModel, ServerResponseInterface mCallInterface, String TAG) {
        setCallbacks(mCallInterface);
        String query = null;

        postData(context, insertAccountModel, ServerConstants.URL_insert_User_account, query, TAG);
    }

    public void insertRecentCoupon(Context context,InsertRecentCouponModel insertRecentCouponModel, ServerResponseInterface mCallInterface, String TAG) {
        setCallbacks(mCallInterface);
        String query = null;

        postData(context, insertRecentCouponModel, ServerConstants.URL_insert_Recent_Coupon, query, TAG);
    }
    public void insertMembership(Context context, ServerResponseInterface mCallInterface, String TAG) {
        setCallbacks(mCallInterface);
        String query = null;

        postData(context,new MembershipModel() , ServerConstants.URL_insert_membership, query, TAG);
    }

    public void insertReview(Context context,InsertReviewModel insertReviewModel, ServerResponseInterface mCallInterface, String TAG) {
        setCallbacks(mCallInterface);
        String query = null;

        postData(context, insertReviewModel, ServerConstants.URL_InsertReview, query, TAG);
    }
    public void getMembershipList(Context context, ServerResponseInterface mCallInterface, String TAG) {
        setCallbacks(mCallInterface);
        String query = null;
        //query = query != null ? query + "&index=" + index : "index=" + index;
        // query = query != null ? query + "&size=" + size : "size=" + size;
        // query = query != null ? query + "&status=" + status : "status=" + status;

        getData(context, new MembershipMainModel(), ServerConstants.URL_membership_list, query, TAG);
    }



    public void getRecentCouponList(Context context, ServerResponseInterface mCallInterface, String TAG) {
        setCallbacks(mCallInterface);
        String query = null;
        //query = query != null ? query + "&index=" + index : "index=" + index;
       // query = query != null ? query + "&size=" + size : "size=" + size;
       // query = query != null ? query + "&status=" + status : "status=" + status;

        getData(context, new RecentCouponMainModel(), ServerConstants.URL_insert_Coupon_list, query, TAG);
    }


    public void getVendorProduct(Context context, ProductPostModel productPostModel, ServerResponseInterface mCallInterface, String TAG) {
        setCallbacks(mCallInterface);
       // postData(context, productPostModel, ServerConstants.URL_VendorProduct, TAG);
    }

    public void insertCart(Context context, CartPostModel cartPostModel, ServerResponseInterface mCallInterface, String TAG) {
        setCallbacks(mCallInterface);
        //postData(context, cartPostModel, ServerConstants.URL_InsertCart, TAG);
    }

    public void getWishList(Context context, ProductPostModel productPostModel, ServerResponseInterface mCallInterface, String TAG) {
        setCallbacks(mCallInterface);
        String query = null;
        if (productPostModel != null) {
            if (productPostModel.getProductId() != null) {
                query = query != null ? query + "&product_id=" + productPostModel.getProductId() : "product_id=" + productPostModel.getProductId();
            }
            if (productPostModel.getIndex() != null) {
                query = query != null ? query + "&index=" + productPostModel.getIndex() : "index=" + productPostModel.getIndex();
            }
            if (productPostModel.getSize() != null) {
                query = query != null ? query + "&size=" + productPostModel.getSize() : "size=" + productPostModel.getSize();
            }
        }
        getData(context, new WishListModel(), ServerConstants.URL_Wishlist, query, TAG);
    }


    public void checkLocation(Context context, LocationCheckPostModel locationCheckPostModel, ServerResponseInterface mCallInterface, String TAG) {
        setCallbacks(mCallInterface);
       // postData(context, locationCheckPostModel, ServerConstants.URL_LocationCheck, TAG);
    }

    public void getCityList(Context context, ServerResponseInterface mCallInterface, String TAG) {
        setCallbacks(mCallInterface);
        String query = null;
        getData(context, new CityListMainModel(), ServerConstants.URL_GetCityList, null, TAG);
    }

    public void getOrderList(Context context, ProductPostModel productPostModel, ServerResponseInterface mCallInterface, String TAG) {
        setCallbacks(mCallInterface);
        String query = null;
        if (productPostModel != null) {
            query = query != null ? query + "&index=" + productPostModel.getIndex() : "index=" + productPostModel.getIndex();
            query = query != null ? query + "&size=" + productPostModel.getSize() : "size=" + productPostModel.getSize();
        }
       // getData(context, new OrderListMainModel(), ServerConstants.URL_OrderList, query, TAG);
    }


    public void getCart(Context context, ServerResponseInterface mCallInterface, String TAG) {
        setCallbacks(mCallInterface);
        String query = null;
      //  getData(context, new CartMainModel(), ServerConstants.URL_GetCart, query, TAG);
    }

    public void getRelatedProducts(Context context, String productId, ServerResponseInterface mCallInterface, String TAG) {
        setCallbacks(mCallInterface);
        String query = null;
        query = query != null ? query + "&product_id=" + productId : "product_id=" + productId;
       // getData(context, new RelatedProductsListModel(), ServerConstants.URL_RelatedProduct, query, TAG);
    }

    public void updateCart(Context context, CartPostModel cartPostModel, ServerResponseInterface mCallInterface, String TAG) {
        setCallbacks(mCallInterface);
       // postData(context, cartPostModel, ServerConstants.URL_UpdateCart, TAG);
    }

    public void removeWishList(Context context, WishlistPostModel wishlistPostModel, ServerResponseInterface mCallInterface, String TAG) {
        setCallbacks(mCallInterface);
        String query = null;
        query = query != null ? query + "&id_product=" + wishlistPostModel.getProductId() : "id_product=" + wishlistPostModel.getProductId();
        query = query != null ? query + "&id_variation=" + wishlistPostModel.getVariationId() : "id_variation=" + wishlistPostModel.getVariationId();
        query = query != null ? query + "&status=" + wishlistPostModel.getStatus() : "status=" + wishlistPostModel.getStatus();
        //getData(context, new StatusMainModel(), ServerConstants.URL_RemoveWishlist,query, TAG);
    }

    public void cartToWishList(Context context, WishlistPostModel wishlistPostModel, ServerResponseInterface mCallInterface, String TAG) {
        setCallbacks(mCallInterface);
        String query = null;
        query = query != null ? query + "&id_product=" + wishlistPostModel.getProductId() : "id_product=" + wishlistPostModel.getProductId();
        query = query != null ? query + "&id_variation=" + wishlistPostModel.getVariationId() : "id_variation=" + wishlistPostModel.getVariationId();
        query = query != null ? query + "&status=" + wishlistPostModel.getStatus() : "status=" + wishlistPostModel.getStatus();
       // getData(context, new StatusMainModel(), ServerConstants.URL_CartToWishlist,query, TAG);
    }

    public void addCouponCode(Context context, CouponCodePostModel couponCodePostModel, ServerResponseInterface mCallInterface, String TAG) {
        setCallbacks(mCallInterface);
        //postData(context, couponCodePostModel, ServerConstants.URL_AddCouponCOde, TAG);
    }

    public void addUserAddress(Context context, UserAddressModel userAddressModel, ServerResponseInterface mCallInterface, String TAG) {
        setCallbacks(mCallInterface);
       // postData(context, userAddressModel, ServerConstants.URL_AddUserAddress, TAG);
    }

    public void updateUserAddress(Context context, UserAddressModel userAddressModel, ServerResponseInterface mCallInterface, String TAG) {
        setCallbacks(mCallInterface);
        //postData(context, userAddressModel, ServerConstants.URL_updateUserAddress, TAG);
    }

    public void deleteUserAddress(Context context, UserAddressModel userAddressModel, ServerResponseInterface mCallInterface, String TAG) {
        setCallbacks(mCallInterface);
        String query = null;
        query = query != null ? query + "&id=" + userAddressModel.getId() : "id=" + userAddressModel.getId();
       // getData(context, userAddressModel, ServerConstants.URL_DeleteUserAddress,query, TAG);
    }

    public void getUserAddress(Context context, ServerResponseInterface mCallInterface, String TAG) {
        setCallbacks(mCallInterface);
        String query = null;
//        query = query != null ? query + "&type=" + "1" : "type=" + "1";
     //   String url = ServerConstants.URL_GetUserAddress;
      //  getData(context, new UserAddressListModel(), url, query, TAG);
    }

    public void getDefaultUserAddress(Context context, ServerResponseInterface mCallInterface, String TAG) {
        setCallbacks(mCallInterface);
        String query = null;
        query = query != null ? query + "&type=" + "1" : "type=" + "1";
      //  String url = ServerConstants.URL_GetUserAddress;
       // getData(context, new UserAddressListModel(), url, query, TAG);
    }

    public void submitOrder(Context context, OrderSubmitModel orderSubmitModel, ServerResponseInterface mCallInterface, String TAG) {
        setCallbacks(mCallInterface);
        //postData(context, orderSubmitModel, ServerConstants.URL_OrderSubmit, TAG);
    }

    public void search(Context context, @NonNull SearchModel model, ServerResponseInterface mCallInterface, String TAG) {
        setCallbacks(mCallInterface);
       // postData(context, model, ServerConstants.URL_Search, null, TAG);
    }

    public void getWalletHistoryList(Context context, OrderPostModel orderPostModel, ServerResponseInterface mCallInterface, String TAG) {
        setCallbacks(mCallInterface);
        String query = null;
        if (orderPostModel != null) {
            query = query != null ? query + "&index=" + orderPostModel.getIndex() : "index=" + orderPostModel.getIndex();
            query = query != null ? query + "&size=" + orderPostModel.getSize() : "size=" + orderPostModel.getSize();
        }
        //getData(context, new WalletMainModel(), ServerConstants.URL_WalletHistory, query, TAG);
    }

    public void getOrderDetails(Context context, String orderId, ServerResponseInterface mCallInterface, String TAG) {
        setCallbacks(mCallInterface);
        String query = null;
        query = query != null ? query + "&order_id=" + orderId : "order_id=" + orderId;
        //getData(context, new OrderDetailsMainModel(), ServerConstants.URL_OrderDetails, query, TAG);
    }

    public void getTicketDetails(Context context, String ticketId, ServerResponseInterface mCallInterface, String TAG) {
        setCallbacks(mCallInterface);
        String query = null;
        query = query != null ? query + "&id=" + ticketId : "id=" + ticketId;
        //getData(context, new TicketsDetailModel(), ServerConstants.URL_TicketDetails, query, TAG);
    }

    public void updateOrderStatus(Context context, OrderStatusUpdateModel orderStatusUpdateModel, ServerResponseInterface mCallInterface, String TAG) {
        setCallbacks(mCallInterface);
       // postData(context, orderStatusUpdateModel, ServerConstants.URL_UpdateOrderStatus, TAG);
    }

    public void onlineWalletCredit(Context context, WalletAmountCreditModel walletAmountCreditModel, ServerResponseInterface mCallInterface, String TAG) {
        setCallbacks(mCallInterface);
       // postData(context, walletAmountCreditModel, ServerConstants.URL_OnlineWalletCredit, TAG);
    }

    public void getWishList(Context context, WishlistPostModel wishlistPostModel, ServerResponseInterface mCallInterface, String TAG) {
        setCallbacks(mCallInterface);
        String query = null;
        if (wishlistPostModel != null) {
            query = query != null ? query + "&index=" + wishlistPostModel.getIndex() : "index=" + wishlistPostModel.getIndex();
            query = query != null ? query + "&size=" + wishlistPostModel.getSize() : "size=" + wishlistPostModel.getSize();
        }
      //  getData(context, new WishListMainModel(), ServerConstants.URL_GetWishlist, query, TAG);
    }

    public void changePassword(Context context, ChangePasswordModel changePasswordModel, ServerResponseInterface mCallInterface, String TAG) {
        setCallbacks(mCallInterface);
       // postData(context, changePasswordModel, ServerConstants.URL_ForgotMyPassword, TAG);
    }

    public void forgotPassword(Context context, LoginModel loginModel, ServerResponseInterface mCallInterface, String TAG) {
        setCallbacks(mCallInterface);
        String query = null;
        //query = query!=null? query+"&user_type="+loginModel.getUserType() : "user_type="+loginModel.getUserType();
        GlobalFunctions.removeSharedPreferenceKey(context, GlobalVariables.SHARED_PREFERENCE_COOKIE);
       // String URL = ServerConstants.URL_ForgotMyPassword;
        //postData(context, loginModel, URL, query, TAG);
    }


}
