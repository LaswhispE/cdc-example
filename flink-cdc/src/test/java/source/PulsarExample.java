package source;

import java.io.IOException;
import java.text.ParseException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class PulsarExample {
    public static void main(String[] args) throws ParseException, IOException {
        String json = "{\"id\":367034,\"id_hash\":\"c57423d344bcfacc605d0574725937c7\",\"zid\":1,\"sid\":17,\"bid\":0,\"cid\":0,\"pid\":0,\"local_sku\":\"\",\"local_name\":\"\",\"item_name\":\"HEETA 2-Pack Corner Shower Caddy, Bathroom Shelves with Hooks, SUS304 Rustproof Stainless Steel Storage Organiser for Toilet Kitchen Dorm, Adhesive Sticker Included, 90 degree right angle (Silver)\",\"item_description\":\"\",\"listing_id\":\"\",\"seller_sku\":\"L8-B5RY-L6MN\",\"price\":\"0\",\"quantity\":0,\"open_date\":\"\",\"open_date_display\":\"\",\"open_date_time\":0,\"image_url\":\"\",\"item_is_marketplace\":\"\",\"product_id_type\":0,\"zshop_shipping_fee\":\"\",\"item_note\":\"\",\"item_condition\":0,\"zshop_category1\":\"\",\"zshop_browse_path\":\"0\",\"zshop_storefront_feature\":\"\",\"is_parent\":0,\"parent_asin\":\"\",\"asin1\":\"B07XMN6N7R\",\"asin2\":\"\",\"asin3\":\"\",\"will_ship_internationally\":\"\",\"expedited_shipping\":\"\",\"zshop_boldface\":\"\",\"product_id\":\"\",\"bid_for_featured_placement\":\"\",\"add_delete\":\"\",\"pending_quantity\":0,\"fulfillment_channel\":\"AMAZON_NA\",\"merchant_shipping_group\":\"\",\"business_price\":\"\",\"quantity_price_type\":\"\",\"quantity_lower_bound_1\":\"\",\"quantity_price_1\":\"\",\"quantity_lower_bound_2\":\"\",\"quantity_price_2\":\"\",\"quantity_lower_bound_3\":\"\",\"quantity_price_3\":\"\",\"quantity_lower_bound_4\":\"\",\"quantity_price_4\":\"\",\"quantity_lower_bound_5\":\"\",\"quantity_price_5\":\"\",\"listing_price\":\"\",\"landed_price\":\"\",\"currency_code\":\"\",\"small_image_url\":\"\",\"fnsku\":\"X0012OCLW1\",\"total_fulfillable_quantity\":0,\"afn_warehouse_quantity\":0,\"afn_warehouse_quantity_update\":\"\",\"afn_fulfillable_quantity\":0,\"afn_fulfillable_quantity_update\":\"\",\"afn_unsellable_quantity\":0,\"afn_unsellable_quantity_update\":\"\",\"reserved_customerorders\":0,\"reserved_customerorders_update\":\"\",\"reserved_fc_transfers\":0,\"reserved_fc_transfers_update\":\"\",\"reserved_fc_processing\":0,\"reserved_fc_processing_update\":\"\",\"afn_inbound_working_quantity\":0,\"afn_inbound_working_quantity_update\":\"\",\"afn_inbound_shipped_quantity\":0,\"afn_inbound_shipped_quantity_update\":\"\",\"afn_inbound_receiving_quantity\":0,\"afn_inbound_receiving_quantity_update\":\"\",\"total_sales_quantity\":0,\"total_sales_amount\":\"\",\"total_sales_stat_begin_time\":\"\",\"total_sales_stat_end_time\":\"\",\"unsupported_payment\":\"\",\"is_delete\":1,\"status\":-1,\"gl_type\":\"\",\"brand_name\":\"\",\"product_type\":\"\",\"listing_update_date\":\"\",\"last_get_matching_product_time\":0,\"last_get_my_price_for_sku_time\":0,\"sales_rank\":0,\"category\":\"\",\"small_rank\":\"\",\"reviews_num\":0,\"stars\":0.0,\"sales_total_30\":0,\"sales_total_30_version\":0,\"gmt_modified\":1688336005000,\"gmt_create\":1688335935000,\"company_id\":\"1\",\"v_uuid\":\"7c2874d3-71af-4647-8e51-034384cd8ee5\"}";

        /**
         * ObjectMapper支持从byte[]、File、InputStream、字符串等数据的JSON反序列化。
         */
        ObjectMapper mapper = new ObjectMapper();
        ProductTestErp1 user = mapper.readValue(json, ProductTestErp1.class);
        System.out.println(user.getId());
    }
}