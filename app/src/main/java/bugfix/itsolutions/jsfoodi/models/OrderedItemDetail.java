package bugfix.itsolutions.jsfoodi.models;

import java.util.ArrayList;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: OrderedItemDetail.kt */
/* loaded from: classes4.dex */
public final class OrderedItemDetail {
    private String OTP;
    private String ordered_id;
    private ArrayList<String> ordered_items;
    private String ordered_restaurant_name;
    private String ordered_restaurant_spotimage;
    private String ordered_time;
    private String total_amount;

    public OrderedItemDetail() {
        this(null, null, null, null, null, null, null,127, null);
    }

    public static /* synthetic */ OrderedItemDetail copy$default(OrderedItemDetail orderedItemDetail, String str, ArrayList arrayList, String str2, String str3, String str4, String str5, String str6, int i, Object obj) {
        if ((i & 1) != 0) {
            str = orderedItemDetail.ordered_id;
        }
        ArrayList<String> arrayList2 = arrayList;
        if ((i & 2) != 0) {
            arrayList2 = orderedItemDetail.ordered_items;
        }
        ArrayList arrayList3 = arrayList2;
        if ((i & 4) != 0) {
            str2 = orderedItemDetail.ordered_restaurant_name;
        }
        String str7 = str2;
        if ((i & 8) != 0) {
            str3 = orderedItemDetail.ordered_time;
        }
        String str8 = str3;
        if ((i & 16) != 0) {
            str4 = orderedItemDetail.total_amount;
        }
        String str9 = str4;
        if ((i & 32) != 0) {
            str5 = orderedItemDetail.ordered_restaurant_spotimage;
        }
        String str10 = str5;
        if ((i & 64) != 0) {
            str6 = orderedItemDetail.OTP;
        }
        return orderedItemDetail.copy(str, arrayList3, str7, str8, str9, str10, str6);
    }

    public final String component1() {
        return this.ordered_id;
    }

    public final ArrayList<String> component2() {
        return this.ordered_items;
    }

    public final String component3() {
        return this.ordered_restaurant_name;
    }

    public final String component4() {
        return this.ordered_time;
    }

    public final String component5() {
        return this.total_amount;
    }

    public final String component6() {
        return this.ordered_restaurant_spotimage;
    }

    public final String component7() {
        return this.OTP;
    }

    public final OrderedItemDetail copy(String str, ArrayList<String> arrayList, String str2, String str3, String str4, String str5, String str6) {
        return new OrderedItemDetail(str, arrayList, str2, str3, str4, str5, str6);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof OrderedItemDetail) {
            OrderedItemDetail orderedItemDetail = (OrderedItemDetail) obj;
            return Intrinsics.areEqual(this.ordered_id, orderedItemDetail.ordered_id) && Intrinsics.areEqual(this.ordered_items, orderedItemDetail.ordered_items) && Intrinsics.areEqual(this.ordered_restaurant_name, orderedItemDetail.ordered_restaurant_name) && Intrinsics.areEqual(this.ordered_time, orderedItemDetail.ordered_time) && Intrinsics.areEqual(this.total_amount, orderedItemDetail.total_amount) && Intrinsics.areEqual(this.ordered_restaurant_spotimage, orderedItemDetail.ordered_restaurant_spotimage) && Intrinsics.areEqual(this.OTP, orderedItemDetail.OTP);
        }
        return false;
    }

    public int hashCode() {
        String str = this.ordered_id;
        int hashCode = (str == null ? 0 : str.hashCode()) * 31;
        ArrayList<String> arrayList = this.ordered_items;
        int hashCode2 = (hashCode + (arrayList == null ? 0 : arrayList.hashCode())) * 31;
        String str2 = this.ordered_restaurant_name;
        int hashCode3 = (hashCode2 + (str2 == null ? 0 : str2.hashCode())) * 31;
        String str3 = this.ordered_time;
        int hashCode4 = (hashCode3 + (str3 == null ? 0 : str3.hashCode())) * 31;
        String str4 = this.total_amount;
        int hashCode5 = (hashCode4 + (str4 == null ? 0 : str4.hashCode())) * 31;
        String str5 = this.ordered_restaurant_spotimage;
        int hashCode6 = (hashCode5 + (str5 == null ? 0 : str5.hashCode())) * 31;
        String str6 = this.OTP;
        return hashCode6 + (str6 != null ? str6.hashCode() : 0);
    }

    public String toString() {
        return "OrderedItemDetail(ordered_id=" + this.ordered_id + ", ordered_items=" + this.ordered_items + ", ordered_restaurant_name=" + this.ordered_restaurant_name + ", ordered_time=" + this.ordered_time + ", total_amount=" + this.total_amount + ", ordered_restaurant_spotimage=" + this.ordered_restaurant_spotimage + ", OTP=" + this.OTP + ')';
    }

    public OrderedItemDetail(String str, ArrayList<String> arrayList, String str2, String str3, String str4, String str5, String str6) {
        this.ordered_id = str;
        this.ordered_items = arrayList;
        this.ordered_restaurant_name = str2;
        this.ordered_time = str3;
        this.total_amount = str4;
        this.ordered_restaurant_spotimage = str5;
        this.OTP = str6;
    }

    public /* synthetic */ OrderedItemDetail(String str, ArrayList arrayList, String str2, String str3, String str4, String str5, String str6, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? null : str, (i & 2) != 0 ? null : arrayList, (i & 4) != 0 ? null : str2, (i & 8) != 0 ? null : str3, (i & 16) != 0 ? null : str4, (i & 32) != 0 ? null : str5, (i & 64) != 0 ? null : str6);
    }

    public final String getOrdered_id() {
        return this.ordered_id;
    }

    public final void setOrdered_id(String str) {
        this.ordered_id = str;
    }

    public final ArrayList<String> getOrdered_items() {
        return this.ordered_items;
    }

    public final void setOrdered_items(ArrayList<String> arrayList) {
        this.ordered_items = arrayList;
    }

    public final String getOrdered_restaurant_name() {
        return this.ordered_restaurant_name;
    }

    public final void setOrdered_restaurant_name(String str) {
        this.ordered_restaurant_name = str;
    }

    public final String getOrdered_time() {
        return this.ordered_time;
    }

    public final void setOrdered_time(String str) {
        this.ordered_time = str;
    }

    public final String getTotal_amount() {
        return this.total_amount;
    }

    public final void setTotal_amount(String str) {
        this.total_amount = str;
    }

    public final String getOrdered_restaurant_spotimage() {
        return this.ordered_restaurant_spotimage;
    }

    public final void setOrdered_restaurant_spotimage(String str) {
        this.ordered_restaurant_spotimage = str;
    }

    public final String getOTP() {
        return this.OTP;
    }

    public final void setOTP(String str) {
        this.OTP = str;
    }
}
