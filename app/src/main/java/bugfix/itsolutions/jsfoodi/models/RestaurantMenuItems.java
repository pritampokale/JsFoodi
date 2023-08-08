package bugfix.itsolutions.jsfoodi.models;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: RestaurantMenuItems.kt */
/* loaded from: classes4.dex */
public final class RestaurantMenuItems {
    private String category;
    private String is_active;
    private String is_added;
    private String name;
    private String price;
    private String specification;

    public RestaurantMenuItems() {
        this(null, null, null, null, null, null, 63, null);
    }

    public static /* synthetic */ RestaurantMenuItems copy$default(RestaurantMenuItems restaurantMenuItems, String str, String str2, String str3, String str4, String str5, String str6, int i, Object obj) {
        if ((i & 1) != 0) {
            str = restaurantMenuItems.name;
        }
        if ((i & 2) != 0) {
            str2 = restaurantMenuItems.price;
        }
        String str7 = str2;
        if ((i & 4) != 0) {
            str3 = restaurantMenuItems.specification;
        }
        String str8 = str3;
        if ((i & 8) != 0) {
            str4 = restaurantMenuItems.category;
        }
        String str9 = str4;
        if ((i & 16) != 0) {
            str5 = restaurantMenuItems.is_active;
        }
        String str10 = str5;
        if ((i & 32) != 0) {
            str6 = restaurantMenuItems.is_added;
        }
        return restaurantMenuItems.copy(str, str7, str8, str9, str10, str6);
    }

    public final String component1() {
        return this.name;
    }

    public final String component2() {
        return this.price;
    }

    public final String component3() {
        return this.specification;
    }

    public final String component4() {
        return this.category;
    }

    public final String component5() {
        return this.is_active;
    }

    public final String component6() {
        return this.is_added;
    }

    public final RestaurantMenuItems copy(String str, String str2, String str3, String str4, String str5, String str6) {
        return new RestaurantMenuItems(str, str2, str3, str4, str5, str6);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof RestaurantMenuItems) {
            RestaurantMenuItems restaurantMenuItems = (RestaurantMenuItems) obj;
            return Intrinsics.areEqual(this.name, restaurantMenuItems.name) && Intrinsics.areEqual(this.price, restaurantMenuItems.price) && Intrinsics.areEqual(this.specification, restaurantMenuItems.specification) && Intrinsics.areEqual(this.category, restaurantMenuItems.category) && Intrinsics.areEqual(this.is_active, restaurantMenuItems.is_active) && Intrinsics.areEqual(this.is_added, restaurantMenuItems.is_added);
        }
        return false;
    }

    public int hashCode() {
        String str = this.name;
        int hashCode = (str == null ? 0 : str.hashCode()) * 31;
        String str2 = this.price;
        int hashCode2 = (hashCode + (str2 == null ? 0 : str2.hashCode())) * 31;
        String str3 = this.specification;
        int hashCode3 = (hashCode2 + (str3 == null ? 0 : str3.hashCode())) * 31;
        String str4 = this.category;
        int hashCode4 = (hashCode3 + (str4 == null ? 0 : str4.hashCode())) * 31;
        String str5 = this.is_active;
        int hashCode5 = (hashCode4 + (str5 == null ? 0 : str5.hashCode())) * 31;
        String str6 = this.is_added;
        return hashCode5 + (str6 != null ? str6.hashCode() : 0);
    }

    public String toString() {
        return "RestaurantMenuItems(name=" + this.name + ", price=" + this.price + ", specification=" + this.specification + ", category=" + this.category + ", is_active=" + this.is_active + ", is_added=" + this.is_added + ')';
    }

    public RestaurantMenuItems(String str, String str2, String str3, String str4, String str5, String str6) {
        this.name = str;
        this.price = str2;
        this.specification = str3;
        this.category = str4;
        this.is_active = str5;
        this.is_added = str6;
    }

    public /* synthetic */ RestaurantMenuItems(String str, String str2, String str3, String str4, String str5, String str6, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? null : str, (i & 2) != 0 ? null : str2, (i & 4) != 0 ? null : str3, (i & 8) != 0 ? null : str4, (i & 16) != 0 ? null : str5, (i & 32) != 0 ? null : str6);
    }

    public final String getName() {
        return this.name;
    }

    public final void setName(String str) {
        this.name = str;
    }

    public final String getPrice() {
        return this.price;
    }

    public final void setPrice(String str) {
        this.price = str;
    }

    public final String getSpecification() {
        return this.specification;
    }

    public final void setSpecification(String str) {
        this.specification = str;
    }

    public final String getCategory() {
        return this.category;
    }

    public final void setCategory(String str) {
        this.category = str;
    }

    public final String is_active() {
        return this.is_active;
    }

    public final void set_active(String str) {
        this.is_active = str;
    }

    public final String is_added() {
        return this.is_added;
    }

    public final void set_added(String str) {
        this.is_added = str;
    }
}
