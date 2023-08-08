package bugfix.itsolutions.jsfoodi.models;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: CartItemDetail.kt */
/* loaded from: classes4.dex */
public final class CartItemDetail {
    private String item_count;
    private String select_name;
    private String select_price;
    private String select_specification;

    public CartItemDetail() {
        this(null, null, null, null, 15, null);
    }

    public static /* synthetic */ CartItemDetail copy$default(CartItemDetail cartItemDetail, String str, String str2, String str3, String str4, int i, Object obj) {
        if ((i & 1) != 0) {
            str = cartItemDetail.select_name;
        }
        if ((i & 2) != 0) {
            str2 = cartItemDetail.select_price;
        }
        if ((i & 4) != 0) {
            str3 = cartItemDetail.select_specification;
        }
        if ((i & 8) != 0) {
            str4 = cartItemDetail.item_count;
        }
        return cartItemDetail.copy(str, str2, str3, str4);
    }

    public final String component1() {
        return this.select_name;
    }

    public final String component2() {
        return this.select_price;
    }

    public final String component3() {
        return this.select_specification;
    }

    public final String component4() {
        return this.item_count;
    }

    public final CartItemDetail copy(String str, String str2, String str3, String str4) {
        return new CartItemDetail(str, str2, str3, str4);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof CartItemDetail) {
            CartItemDetail cartItemDetail = (CartItemDetail) obj;
            return Intrinsics.areEqual(this.select_name, cartItemDetail.select_name) && Intrinsics.areEqual(this.select_price, cartItemDetail.select_price) && Intrinsics.areEqual(this.select_specification, cartItemDetail.select_specification) && Intrinsics.areEqual(this.item_count, cartItemDetail.item_count);
        }
        return false;
    }

    public int hashCode() {
        String str = this.select_name;
        int hashCode = (str == null ? 0 : str.hashCode()) * 31;
        String str2 = this.select_price;
        int hashCode2 = (hashCode + (str2 == null ? 0 : str2.hashCode())) * 31;
        String str3 = this.select_specification;
        int hashCode3 = (hashCode2 + (str3 == null ? 0 : str3.hashCode())) * 31;
        String str4 = this.item_count;
        return hashCode3 + (str4 != null ? str4.hashCode() : 0);
    }

    public String toString() {
        return "CartItemDetail(select_name=" + this.select_name + ", select_price=" + this.select_price + ", select_specification=" + this.select_specification + ", item_count=" + this.item_count + ')';
    }

    public CartItemDetail(String str, String str2, String str3, String str4) {
        this.select_name = str;
        this.select_price = str2;
        this.select_specification = str3;
        this.item_count = str4;
    }

    public /* synthetic */ CartItemDetail(String str, String str2, String str3, String str4, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? null : str, (i & 2) != 0 ? null : str2, (i & 4) != 0 ? null : str3, (i & 8) != 0 ? null : str4);
    }

    public final String getSelect_name() {
        return this.select_name;
    }

    public final void setSelect_name(String str) {
        this.select_name = str;
    }

    public final String getSelect_price() {
        return this.select_price;
    }

    public final void setSelect_price(String str) {
        this.select_price = str;
    }

    public final String getSelect_specification() {
        return this.select_specification;
    }

    public final void setSelect_specification(String str) {
        this.select_specification = str;
    }

    public final String getItem_count() {
        return this.item_count;
    }

    public final void setItem_count(String str) {
        this.item_count = str;
    }
}
