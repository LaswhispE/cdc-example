package source;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

public class ProductTestErp1 {
    private BigInteger id;
    private int zid;
    private int sid;
    private String id_hash;
    private int bid;
    private int cid;
    private int pid;
    private String local_sku;
    private String local_name;
    private String item_name;
    private String item_description;
    private String listing_id;
    private String seller_sku;
    private String price;
    private int quantity;
    private String open_date;
    private String open_date_display;
    private int open_date_time;
    private String image_url; // Assuming the field was mistyped as 'img_url'
    private String item_is_marketplace;
    private int product_id_type;
    private String zshop_shipping_fee;
    private String item_note;
    private int item_condition;
    private String zshop_category1;
    private String zshop_browse_path;
    private String zshop_storefront_feature;
    private byte is_parent;
    private String parent_asin;
    private String asin1;
    private String asin2;
    private String asin3;
    private String will_ship_internationally;
    private String expedited_shipping;
    private String zshop_boldface;
    private String product_id;
    private String bid_for_featured_placement;
    private String add_delete;
    private int pending_quantity;
    private String fulfillment_channel;
    private String merchant_shipping_group;
    private String business_price;
    private String quantity_price_type;
    private String quantity_lower_bound_1;
    private String quantity_price_1;
    private String quantity_lower_bound_2;
    private String quantity_price_2;
    private String quantity_lower_bound_3;
    private String quantity_price_3;
    private String quantity_lower_bound_4;
    private String quantity_price_4;
    private String quantity_lower_bound_5;
    private String quantity_price_5;
    private String listing_price;
    private String landed_price;
    private String currency_code;
    private String small_image_url;
    private String fnsku;
    private int total_fulfillable_quantity;
    private int afn_warehouse_quantity;
    private String afn_warehouse_quantity_update;
    private int afn_fulfillable_quantity;
    private String afn_fulfillable_quantity_update;
    private int afn_unsellable_quantity;
    private String afn_unsellable_quantity_update;
    private int reserved_customerorders;
    private String reserved_customerorders_update;
    private int reserved_fc_transfers;
    private String reserved_fc_transfers_update;
    private int reserved_fc_processing;
    private String reserved_fc_processing_update;
    private int afn_inbound_working_quantity;
    private String afn_inbound_working_quantity_update;
    private int afn_inbound_shipped_quantity;
    private String afn_inbound_shipped_quantity_update;
    private int afn_inbound_receiving_quantity;
    private String afn_inbound_receiving_quantity_update;
    private int total_sales_quantity;
    private String total_sales_amount;
    private String total_sales_stat_begin_time;
    private String total_sales_stat_end_time;
    private String unsupported_payment;
    private int is_delete;
    private int status;
    private String gl_type;
    private String brand_name;
    private String product_type;
    private String listing_update_date;
    private int last_get_matching_product_time;
    private int last_get_my_price_for_sku_time;
    private int sales_rank;
    private String category;
    private String small_rank;
    private int reviews_num;
    private float stars;
    private int sales_total_30;
    private int sales_total_30_version;
    private Date gmt_modified;
    private Date gmt_create;
    private BigInteger company_id;
    private String v_uuid;

    public ProductTestErp1(BigInteger id, int zid, int sid, String id_hash, int bid, int cid, int pid, String local_sku, String local_name, String item_name, String item_description, String listing_id, String seller_sku, String price, int quantity, String open_date, String open_date_display, int open_date_time, String image_url, String item_is_marketplace, int product_id_type, String zshop_shipping_fee, String item_note, int item_condition, String zshop_category1, String zshop_browse_path, String zshop_storefront_feature, byte is_parent, String parent_asin, String asin1, String asin2, String asin3, String will_ship_internationally, String expedited_shipping, String zshop_boldface, String product_id, String bid_for_featured_placement, String add_delete, int pending_quantity, String fulfillment_channel, String merchant_shipping_group, String business_price, String quantity_price_type, String quantity_lower_bound_1, String quantity_price_1, String quantity_lower_bound_2, String quantity_price_2, String quantity_lower_bound_3, String quantity_price_3, String quantity_lower_bound_4, String quantity_price_4, String quantity_lower_bound_5, String quantity_price_5, String listing_price, String landed_price, String currency_code, String small_image_url, String fnsku, int total_fulfillable_quantity, int afn_warehouse_quantity, String afn_warehouse_quantity_update, int afn_fulfillable_quantity, String afn_fulfillable_quantity_update, int afn_unsellable_quantity, String afn_unsellable_quantity_update, int reserved_customerorders, String reserved_customerorders_update, int reserved_fc_transfers, String reserved_fc_transfers_update, int reserved_fc_processing, String reserved_fc_processing_update, int afn_inbound_working_quantity, String afn_inbound_working_quantity_update, int afn_inbound_shipped_quantity, String afn_inbound_shipped_quantity_update, int afn_inbound_receiving_quantity, String afn_inbound_receiving_quantity_update, int total_sales_quantity, String total_sales_amount, String total_sales_stat_begin_time, String total_sales_stat_end_time, String unsupported_payment, int is_delete, int status, String gl_type, String brand_name, String product_type, String listing_update_date, int last_get_matching_product_time, int last_get_my_price_for_sku_time, int sales_rank, String category, String small_rank, int reviews_num, float stars, int sales_total_30, int sales_total_30_version, Date gmt_modified, Date gmt_create, BigInteger company_id, String v_uuid) {
        this.id = id;
        this.zid = zid;
        this.sid = sid;
        this.id_hash = id_hash;
        this.bid = bid;
        this.cid = cid;
        this.pid = pid;
        this.local_sku = local_sku;
        this.local_name = local_name;
        this.item_name = item_name;
        this.item_description = item_description;
        this.listing_id = listing_id;
        this.seller_sku = seller_sku;
        this.price = price;
        this.quantity = quantity;
        this.open_date = open_date;
        this.open_date_display = open_date_display;
        this.open_date_time = open_date_time;
        this.image_url = image_url;
        this.item_is_marketplace = item_is_marketplace;
        this.product_id_type = product_id_type;
        this.zshop_shipping_fee = zshop_shipping_fee;
        this.item_note = item_note;
        this.item_condition = item_condition;
        this.zshop_category1 = zshop_category1;
        this.zshop_browse_path = zshop_browse_path;
        this.zshop_storefront_feature = zshop_storefront_feature;
        this.is_parent = is_parent;
        this.parent_asin = parent_asin;
        this.asin1 = asin1;
        this.asin2 = asin2;
        this.asin3 = asin3;
        this.will_ship_internationally = will_ship_internationally;
        this.expedited_shipping = expedited_shipping;
        this.zshop_boldface = zshop_boldface;
        this.product_id = product_id;
        this.bid_for_featured_placement = bid_for_featured_placement;
        this.add_delete = add_delete;
        this.pending_quantity = pending_quantity;
        this.fulfillment_channel = fulfillment_channel;
        this.merchant_shipping_group = merchant_shipping_group;
        this.business_price = business_price;
        this.quantity_price_type = quantity_price_type;
        this.quantity_lower_bound_1 = quantity_lower_bound_1;
        this.quantity_price_1 = quantity_price_1;
        this.quantity_lower_bound_2 = quantity_lower_bound_2;
        this.quantity_price_2 = quantity_price_2;
        this.quantity_lower_bound_3 = quantity_lower_bound_3;
        this.quantity_price_3 = quantity_price_3;
        this.quantity_lower_bound_4 = quantity_lower_bound_4;
        this.quantity_price_4 = quantity_price_4;
        this.quantity_lower_bound_5 = quantity_lower_bound_5;
        this.quantity_price_5 = quantity_price_5;
        this.listing_price = listing_price;
        this.landed_price = landed_price;
        this.currency_code = currency_code;
        this.small_image_url = small_image_url;
        this.fnsku = fnsku;
        this.total_fulfillable_quantity = total_fulfillable_quantity;
        this.afn_warehouse_quantity = afn_warehouse_quantity;
        this.afn_warehouse_quantity_update = afn_warehouse_quantity_update;
        this.afn_fulfillable_quantity = afn_fulfillable_quantity;
        this.afn_fulfillable_quantity_update = afn_fulfillable_quantity_update;
        this.afn_unsellable_quantity = afn_unsellable_quantity;
        this.afn_unsellable_quantity_update = afn_unsellable_quantity_update;
        this.reserved_customerorders = reserved_customerorders;
        this.reserved_customerorders_update = reserved_customerorders_update;
        this.reserved_fc_transfers = reserved_fc_transfers;
        this.reserved_fc_transfers_update = reserved_fc_transfers_update;
        this.reserved_fc_processing = reserved_fc_processing;
        this.reserved_fc_processing_update = reserved_fc_processing_update;
        this.afn_inbound_working_quantity = afn_inbound_working_quantity;
        this.afn_inbound_working_quantity_update = afn_inbound_working_quantity_update;
        this.afn_inbound_shipped_quantity = afn_inbound_shipped_quantity;
        this.afn_inbound_shipped_quantity_update = afn_inbound_shipped_quantity_update;
        this.afn_inbound_receiving_quantity = afn_inbound_receiving_quantity;
        this.afn_inbound_receiving_quantity_update = afn_inbound_receiving_quantity_update;
        this.total_sales_quantity = total_sales_quantity;
        this.total_sales_amount = total_sales_amount;
        this.total_sales_stat_begin_time = total_sales_stat_begin_time;
        this.total_sales_stat_end_time = total_sales_stat_end_time;
        this.unsupported_payment = unsupported_payment;
        this.is_delete = is_delete;
        this.status = status;
        this.gl_type = gl_type;
        this.brand_name = brand_name;
        this.product_type = product_type;
        this.listing_update_date = listing_update_date;
        this.last_get_matching_product_time = last_get_matching_product_time;
        this.last_get_my_price_for_sku_time = last_get_my_price_for_sku_time;
        this.sales_rank = sales_rank;
        this.category = category;
        this.small_rank = small_rank;
        this.reviews_num = reviews_num;
        this.stars = stars;
        this.sales_total_30 = sales_total_30;
        this.sales_total_30_version = sales_total_30_version;
        this.gmt_modified = gmt_modified;
        this.gmt_create = gmt_create;
        this.company_id = company_id;
        this.v_uuid = v_uuid;
    }

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public int getZid() {
        return zid;
    }

    public void setZid(int zid) {
        this.zid = zid;
    }

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public String getId_hash() {
        return id_hash;
    }

    public void setId_hash(String id_hash) {
        this.id_hash = id_hash;
    }

    public int getBid() {
        return bid;
    }

    public void setBid(int bid) {
        this.bid = bid;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getLocal_sku() {
        return local_sku;
    }

    public void setLocal_sku(String local_sku) {
        this.local_sku = local_sku;
    }

    public String getLocal_name() {
        return local_name;
    }

    public void setLocal_name(String local_name) {
        this.local_name = local_name;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public String getItem_description() {
        return item_description;
    }

    public void setItem_description(String item_description) {
        this.item_description = item_description;
    }

    public String getListing_id() {
        return listing_id;
    }

    public void setListing_id(String listing_id) {
        this.listing_id = listing_id;
    }

    public String getSeller_sku() {
        return seller_sku;
    }

    public void setSeller_sku(String seller_sku) {
        this.seller_sku = seller_sku;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getOpen_date() {
        return open_date;
    }

    public void setOpen_date(String open_date) {
        this.open_date = open_date;
    }

    public String getOpen_date_display() {
        return open_date_display;
    }

    public void setOpen_date_display(String open_date_display) {
        this.open_date_display = open_date_display;
    }

    public int getOpen_date_time() {
        return open_date_time;
    }

    public void setOpen_date_time(int open_date_time) {
        this.open_date_time = open_date_time;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getItem_is_marketplace() {
        return item_is_marketplace;
    }

    public void setItem_is_marketplace(String item_is_marketplace) {
        this.item_is_marketplace = item_is_marketplace;
    }

    public int getProduct_id_type() {
        return product_id_type;
    }

    public void setProduct_id_type(int product_id_type) {
        this.product_id_type = product_id_type;
    }

    public String getZshop_shipping_fee() {
        return zshop_shipping_fee;
    }

    public void setZshop_shipping_fee(String zshop_shipping_fee) {
        this.zshop_shipping_fee = zshop_shipping_fee;
    }

    public String getItem_note() {
        return item_note;
    }

    public void setItem_note(String item_note) {
        this.item_note = item_note;
    }

    public int getItem_condition() {
        return item_condition;
    }

    public void setItem_condition(int item_condition) {
        this.item_condition = item_condition;
    }

    public String getZshop_category1() {
        return zshop_category1;
    }

    public void setZshop_category1(String zshop_category1) {
        this.zshop_category1 = zshop_category1;
    }

    public String getZshop_browse_path() {
        return zshop_browse_path;
    }

    public void setZshop_browse_path(String zshop_browse_path) {
        this.zshop_browse_path = zshop_browse_path;
    }

    public String getZshop_storefront_feature() {
        return zshop_storefront_feature;
    }

    public void setZshop_storefront_feature(String zshop_storefront_feature) {
        this.zshop_storefront_feature = zshop_storefront_feature;
    }

    public byte getIs_parent() {
        return is_parent;
    }

    public void setIs_parent(byte is_parent) {
        this.is_parent = is_parent;
    }

    public String getParent_asin() {
        return parent_asin;
    }

    public void setParent_asin(String parent_asin) {
        this.parent_asin = parent_asin;
    }

    public String getAsin1() {
        return asin1;
    }

    public void setAsin1(String asin1) {
        this.asin1 = asin1;
    }

    public String getAsin2() {
        return asin2;
    }

    public void setAsin2(String asin2) {
        this.asin2 = asin2;
    }

    public String getAsin3() {
        return asin3;
    }

    public void setAsin3(String asin3) {
        this.asin3 = asin3;
    }

    public String getWill_ship_internationally() {
        return will_ship_internationally;
    }

    public void setWill_ship_internationally(String will_ship_internationally) {
        this.will_ship_internationally = will_ship_internationally;
    }

    public String getExpedited_shipping() {
        return expedited_shipping;
    }

    public void setExpedited_shipping(String expedited_shipping) {
        this.expedited_shipping = expedited_shipping;
    }

    public String getZshop_boldface() {
        return zshop_boldface;
    }

    public void setZshop_boldface(String zshop_boldface) {
        this.zshop_boldface = zshop_boldface;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getBid_for_featured_placement() {
        return bid_for_featured_placement;
    }

    public void setBid_for_featured_placement(String bid_for_featured_placement) {
        this.bid_for_featured_placement = bid_for_featured_placement;
    }

    public String getAdd_delete() {
        return add_delete;
    }

    public void setAdd_delete(String add_delete) {
        this.add_delete = add_delete;
    }

    public int getPending_quantity() {
        return pending_quantity;
    }

    public void setPending_quantity(int pending_quantity) {
        this.pending_quantity = pending_quantity;
    }

    public String getFulfillment_channel() {
        return fulfillment_channel;
    }

    public void setFulfillment_channel(String fulfillment_channel) {
        this.fulfillment_channel = fulfillment_channel;
    }

    public String getMerchant_shipping_group() {
        return merchant_shipping_group;
    }

    public void setMerchant_shipping_group(String merchant_shipping_group) {
        this.merchant_shipping_group = merchant_shipping_group;
    }

    public String getBusiness_price() {
        return business_price;
    }

    public void setBusiness_price(String business_price) {
        this.business_price = business_price;
    }

    public String getQuantity_price_type() {
        return quantity_price_type;
    }

    public void setQuantity_price_type(String quantity_price_type) {
        this.quantity_price_type = quantity_price_type;
    }

    public String getQuantity_lower_bound_1() {
        return quantity_lower_bound_1;
    }

    public void setQuantity_lower_bound_1(String quantity_lower_bound_1) {
        this.quantity_lower_bound_1 = quantity_lower_bound_1;
    }

    public String getQuantity_price_1() {
        return quantity_price_1;
    }

    public void setQuantity_price_1(String quantity_price_1) {
        this.quantity_price_1 = quantity_price_1;
    }

    public String getQuantity_lower_bound_2() {
        return quantity_lower_bound_2;
    }

    public void setQuantity_lower_bound_2(String quantity_lower_bound_2) {
        this.quantity_lower_bound_2 = quantity_lower_bound_2;
    }

    public String getQuantity_price_2() {
        return quantity_price_2;
    }

    public void setQuantity_price_2(String quantity_price_2) {
        this.quantity_price_2 = quantity_price_2;
    }

    public String getQuantity_lower_bound_3() {
        return quantity_lower_bound_3;
    }

    public void setQuantity_lower_bound_3(String quantity_lower_bound_3) {
        this.quantity_lower_bound_3 = quantity_lower_bound_3;
    }

    public String getQuantity_price_3() {
        return quantity_price_3;
    }

    public void setQuantity_price_3(String quantity_price_3) {
        this.quantity_price_3 = quantity_price_3;
    }

    public String getQuantity_lower_bound_4() {
        return quantity_lower_bound_4;
    }

    public void setQuantity_lower_bound_4(String quantity_lower_bound_4) {
        this.quantity_lower_bound_4 = quantity_lower_bound_4;
    }

    public String getQuantity_price_4() {
        return quantity_price_4;
    }

    public void setQuantity_price_4(String quantity_price_4) {
        this.quantity_price_4 = quantity_price_4;
    }

    public String getQuantity_lower_bound_5() {
        return quantity_lower_bound_5;
    }

    public void setQuantity_lower_bound_5(String quantity_lower_bound_5) {
        this.quantity_lower_bound_5 = quantity_lower_bound_5;
    }

    public String getQuantity_price_5() {
        return quantity_price_5;
    }

    public void setQuantity_price_5(String quantity_price_5) {
        this.quantity_price_5 = quantity_price_5;
    }

    public String getListing_price() {
        return listing_price;
    }

    public void setListing_price(String listing_price) {
        this.listing_price = listing_price;
    }

    public String getLanded_price() {
        return landed_price;
    }

    public void setLanded_price(String landed_price) {
        this.landed_price = landed_price;
    }

    public String getCurrency_code() {
        return currency_code;
    }

    public void setCurrency_code(String currency_code) {
        this.currency_code = currency_code;
    }

    public String getSmall_image_url() {
        return small_image_url;
    }

    public void setSmall_image_url(String small_image_url) {
        this.small_image_url = small_image_url;
    }

    public String getFnsku() {
        return fnsku;
    }

    public void setFnsku(String fnsku) {
        this.fnsku = fnsku;
    }

    public int getTotal_fulfillable_quantity() {
        return total_fulfillable_quantity;
    }

    public void setTotal_fulfillable_quantity(int total_fulfillable_quantity) {
        this.total_fulfillable_quantity = total_fulfillable_quantity;
    }

    public int getAfn_warehouse_quantity() {
        return afn_warehouse_quantity;
    }

    public void setAfn_warehouse_quantity(int afn_warehouse_quantity) {
        this.afn_warehouse_quantity = afn_warehouse_quantity;
    }

    public String getAfn_warehouse_quantity_update() {
        return afn_warehouse_quantity_update;
    }

    public void setAfn_warehouse_quantity_update(String afn_warehouse_quantity_update) {
        this.afn_warehouse_quantity_update = afn_warehouse_quantity_update;
    }

    public int getAfn_fulfillable_quantity() {
        return afn_fulfillable_quantity;
    }

    public void setAfn_fulfillable_quantity(int afn_fulfillable_quantity) {
        this.afn_fulfillable_quantity = afn_fulfillable_quantity;
    }

    public String getAfn_fulfillable_quantity_update() {
        return afn_fulfillable_quantity_update;
    }

    public void setAfn_fulfillable_quantity_update(String afn_fulfillable_quantity_update) {
        this.afn_fulfillable_quantity_update = afn_fulfillable_quantity_update;
    }

    public int getAfn_unsellable_quantity() {
        return afn_unsellable_quantity;
    }

    public void setAfn_unsellable_quantity(int afn_unsellable_quantity) {
        this.afn_unsellable_quantity = afn_unsellable_quantity;
    }

    public String getAfn_unsellable_quantity_update() {
        return afn_unsellable_quantity_update;
    }

    public void setAfn_unsellable_quantity_update(String afn_unsellable_quantity_update) {
        this.afn_unsellable_quantity_update = afn_unsellable_quantity_update;
    }

    public int getReserved_customerorders() {
        return reserved_customerorders;
    }

    public void setReserved_customerorders(int reserved_customerorders) {
        this.reserved_customerorders = reserved_customerorders;
    }

    public String getReserved_customerorders_update() {
        return reserved_customerorders_update;
    }

    public void setReserved_customerorders_update(String reserved_customerorders_update) {
        this.reserved_customerorders_update = reserved_customerorders_update;
    }

    public int getReserved_fc_transfers() {
        return reserved_fc_transfers;
    }

    public void setReserved_fc_transfers(int reserved_fc_transfers) {
        this.reserved_fc_transfers = reserved_fc_transfers;
    }

    public String getReserved_fc_transfers_update() {
        return reserved_fc_transfers_update;
    }

    public void setReserved_fc_transfers_update(String reserved_fc_transfers_update) {
        this.reserved_fc_transfers_update = reserved_fc_transfers_update;
    }

    public int getReserved_fc_processing() {
        return reserved_fc_processing;
    }

    public void setReserved_fc_processing(int reserved_fc_processing) {
        this.reserved_fc_processing = reserved_fc_processing;
    }

    public String getReserved_fc_processing_update() {
        return reserved_fc_processing_update;
    }

    public void setReserved_fc_processing_update(String reserved_fc_processing_update) {
        this.reserved_fc_processing_update = reserved_fc_processing_update;
    }

    public int getAfn_inbound_working_quantity() {
        return afn_inbound_working_quantity;
    }

    public void setAfn_inbound_working_quantity(int afn_inbound_working_quantity) {
        this.afn_inbound_working_quantity = afn_inbound_working_quantity;
    }

    public String getAfn_inbound_working_quantity_update() {
        return afn_inbound_working_quantity_update;
    }

    public void setAfn_inbound_working_quantity_update(String afn_inbound_working_quantity_update) {
        this.afn_inbound_working_quantity_update = afn_inbound_working_quantity_update;
    }

    public int getAfn_inbound_shipped_quantity() {
        return afn_inbound_shipped_quantity;
    }

    public void setAfn_inbound_shipped_quantity(int afn_inbound_shipped_quantity) {
        this.afn_inbound_shipped_quantity = afn_inbound_shipped_quantity;
    }

    public String getAfn_inbound_shipped_quantity_update() {
        return afn_inbound_shipped_quantity_update;
    }

    public void setAfn_inbound_shipped_quantity_update(String afn_inbound_shipped_quantity_update) {
        this.afn_inbound_shipped_quantity_update = afn_inbound_shipped_quantity_update;
    }

    public int getAfn_inbound_receiving_quantity() {
        return afn_inbound_receiving_quantity;
    }

    public void setAfn_inbound_receiving_quantity(int afn_inbound_receiving_quantity) {
        this.afn_inbound_receiving_quantity = afn_inbound_receiving_quantity;
    }

    public String getAfn_inbound_receiving_quantity_update() {
        return afn_inbound_receiving_quantity_update;
    }

    public void setAfn_inbound_receiving_quantity_update(String afn_inbound_receiving_quantity_update) {
        this.afn_inbound_receiving_quantity_update = afn_inbound_receiving_quantity_update;
    }

    public int getTotal_sales_quantity() {
        return total_sales_quantity;
    }

    public void setTotal_sales_quantity(int total_sales_quantity) {
        this.total_sales_quantity = total_sales_quantity;
    }

    public String getTotal_sales_amount() {
        return total_sales_amount;
    }

    public void setTotal_sales_amount(String total_sales_amount) {
        this.total_sales_amount = total_sales_amount;
    }

    public String getTotal_sales_stat_begin_time() {
        return total_sales_stat_begin_time;
    }

    public void setTotal_sales_stat_begin_time(String total_sales_stat_begin_time) {
        this.total_sales_stat_begin_time = total_sales_stat_begin_time;
    }

    public String getTotal_sales_stat_end_time() {
        return total_sales_stat_end_time;
    }

    public void setTotal_sales_stat_end_time(String total_sales_stat_end_time) {
        this.total_sales_stat_end_time = total_sales_stat_end_time;
    }

    public String getUnsupported_payment() {
        return unsupported_payment;
    }

    public void setUnsupported_payment(String unsupported_payment) {
        this.unsupported_payment = unsupported_payment;
    }

    public int getIs_delete() {
        return is_delete;
    }

    public void setIs_delete(int is_delete) {
        this.is_delete = is_delete;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getGl_type() {
        return gl_type;
    }

    public void setGl_type(String gl_type) {
        this.gl_type = gl_type;
    }

    public String getBrand_name() {
        return brand_name;
    }

    public void setBrand_name(String brand_name) {
        this.brand_name = brand_name;
    }

    public String getProduct_type() {
        return product_type;
    }

    public void setProduct_type(String product_type) {
        this.product_type = product_type;
    }

    public String getListing_update_date() {
        return listing_update_date;
    }

    public void setListing_update_date(String listing_update_date) {
        this.listing_update_date = listing_update_date;
    }

    public int getLast_get_matching_product_time() {
        return last_get_matching_product_time;
    }

    public void setLast_get_matching_product_time(int last_get_matching_product_time) {
        this.last_get_matching_product_time = last_get_matching_product_time;
    }

    public int getLast_get_my_price_for_sku_time() {
        return last_get_my_price_for_sku_time;
    }

    public void setLast_get_my_price_for_sku_time(int last_get_my_price_for_sku_time) {
        this.last_get_my_price_for_sku_time = last_get_my_price_for_sku_time;
    }

    public int getSales_rank() {
        return sales_rank;
    }

    public void setSales_rank(int sales_rank) {
        this.sales_rank = sales_rank;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSmall_rank() {
        return small_rank;
    }

    public void setSmall_rank(String small_rank) {
        this.small_rank = small_rank;
    }

    public int getReviews_num() {
        return reviews_num;
    }

    public void setReviews_num(int reviews_num) {
        this.reviews_num = reviews_num;
    }

    public float getStars() {
        return stars;
    }

    public void setStars(float stars) {
        this.stars = stars;
    }

    public int getSales_total_30() {
        return sales_total_30;
    }

    public void setSales_total_30(int sales_total_30) {
        this.sales_total_30 = sales_total_30;
    }

    public int getSales_total_30_version() {
        return sales_total_30_version;
    }

    public void setSales_total_30_version(int sales_total_30_version) {
        this.sales_total_30_version = sales_total_30_version;
    }

    public Date getGmt_modified() {
        return gmt_modified;
    }

    public void setGmt_modified(Date gmt_modified) {
        this.gmt_modified = gmt_modified;
    }

    public Date getGmt_create() {
        return gmt_create;
    }

    public void setGmt_create(Date gmt_create) {
        this.gmt_create = gmt_create;
    }

    public BigInteger getCompany_id() {
        return company_id;
    }

    public void setCompany_id(BigInteger company_id) {
        this.company_id = company_id;
    }

    public String getV_uuid() {
        return v_uuid;
    }

    public void setV_uuid(String v_uuid) {
        this.v_uuid = v_uuid;
    }


}
