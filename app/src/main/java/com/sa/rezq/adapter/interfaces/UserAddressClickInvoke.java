package com.sa.rezq.adapter.interfaces;


import com.sa.rezq.services.model.UserAddressModel;

public interface UserAddressClickInvoke {
    void OnDeleteInvoke(int position, UserAddressModel model);

    void OnEditInvoke(int position, UserAddressModel model);

    void OnClickInvoke(UserAddressModel model);
}
