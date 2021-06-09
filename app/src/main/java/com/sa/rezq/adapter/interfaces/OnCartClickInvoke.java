package com.sa.rezq.adapter.interfaces;


import com.sa.rezq.services.model.CartPostModel;
import com.sa.rezq.services.model.WishlistPostModel;

public interface OnCartClickInvoke {
    void OnCartWishListClickInvoke(WishlistPostModel wishlistPostModel);
    void OnCartQtyClickInvoke(CartPostModel cartPostModel);
}
