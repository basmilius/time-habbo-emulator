package com.basmilius.time.habbohotel.catalogue;

import com.basmilius.time.habbohotel.enums.CatalogueMode;
import com.basmilius.time.habbohotel.habbo.Habbo;
import com.basmilius.time.communication.messages.ISerialize;
import com.basmilius.time.communication.messages.ServerMessage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public abstract class CataloguePage implements ISerialize, Comparable<CataloguePage>
{

	private int id;
	private int parentId;
	private String captionSave;
	private String caption;
	private int iconColor;
	private int iconImage;
	private int orderNum;
	private boolean isVisible;
	private boolean isEnabled;
	private boolean isClubOnly;
	private boolean isVipOnly;
	private String pageLayout;
	private String pageHeadline;
	private String pageTeaser;
	private String pageSpecial;
	private String pageText1;
	private String pageText2;
	private String pageTextDetails;
	private String pageTextTeaser;
	private List<String> requiredPermissions;
	private List<CatalogueItem> items;
	private CatalogueMode mode;

	protected CataloguePage(final ResultSet result) throws SQLException
	{
		this.id = result.getInt("id");
		this.parentId = result.getInt("parent_id");
		this.captionSave = result.getString("caption_save");
		this.caption = result.getString("caption");
		this.iconColor = result.getInt("icon_color");
		this.iconImage = result.getInt("icon_image");
		this.orderNum = result.getInt("order_num");
		this.isVisible = (result.getString("visible").equals("1"));
		this.isEnabled = (result.getString("enabled").equals("1"));
		this.isClubOnly = (result.getString("club_only").equals("1"));
		this.isVipOnly = (result.getString("vip_only").equals("1"));
		this.pageLayout = result.getString("page_layout");
		this.pageHeadline = result.getString("page_headline");
		this.pageTeaser = result.getString("page_teaser");
		this.pageSpecial = result.getString("page_special");
		this.pageText1 = result.getString("page_text1");
		this.pageText2 = result.getString("page_text2");
		this.pageTextDetails = result.getString("page_text_details");
		this.pageTextTeaser = result.getString("page_text_teaser");
		this.mode = CatalogueMode.fromString(result.getString("catalogue_mode"));

		this.requiredPermissions = new ArrayList<>();
		this.items = new ArrayList<>();

		Collections.addAll(this.requiredPermissions, result.getString("required_permissions").split(","));
	}

	public int getId()
	{
		return this.id;
	}

	public int getParentId()
	{
		return this.parentId;
	}

	public String getCaptionSave()
	{
		return this.captionSave;
	}

	public String getCaption()
	{
		return this.caption;
	}

	public int getIconColor()
	{
		return this.iconColor;
	}

	public int getIconImage()
	{
		return this.iconImage;
	}

	int getOrderNum()
	{
		return this.orderNum;
	}

	public boolean getIsVisible()
	{
		return this.isVisible;
	}

	public boolean getIsEnabled()
	{
		return this.isEnabled;
	}

	public boolean getIsClubOnly()
	{
		return this.isClubOnly;
	}

	public boolean getIsVipOnly()
	{
		return this.isVipOnly;
	}

	public String getPageLayout()
	{
		return this.pageLayout;
	}

	public final String getPageHeadline()
	{
		if (this.pageHeadline.isEmpty())
			return "";
		return this.pageHeadline;
	}

	public final String getPageTeaser()
	{
		if (this.pageTeaser.isEmpty())
			return "";
		return this.pageTeaser;
	}

	public final String getPageSpecial()
	{
		if (this.pageSpecial.isEmpty())
			return "";
		return this.pageSpecial;
	}

	public final String getPageText1()
	{
		if (this.pageText1.isEmpty())
			return "";
		return this.pageText1;
	}

	public final String getPageText2()
	{
		if (this.pageText2.isEmpty())
			return "";
		return this.pageText2;
	}

	public final String getTextDetails()
	{
		if (this.pageTextDetails.isEmpty())
			return "";
		return this.pageTextDetails;
	}

	public final String getTextTeaser()
	{
		if (this.pageTextTeaser.isEmpty())
			return "";
		return this.pageTextTeaser;
	}

	public List<String> getRequiredPermissions()
	{
		return this.requiredPermissions;
	}

	public List<CatalogueItem> getItems()
	{
		return this.items;
	}

	public List<CatalogueItem> getOfferItems()
	{
		return this.getItems().stream().filter(item -> item.getBaseItems().size() > 0 && item.getBaseItems().get(0).getOfferId() > 0).collect(Collectors.toList());
	}

	public CatalogueMode getMode()
	{
		return this.mode;
	}

	public boolean getCanSeePage(final Habbo habbo)
	{
		boolean result = true;

		for (String permission : this.requiredPermissions)
		{
			if (!habbo.getPermissions().contains(permission))
			{
				result = false;
				break;
			}
		}

		return result;
	}

	@Override
	public int compareTo(final CataloguePage p)
	{
		return (this.getOrderNum() - p.getOrderNum());
	}

	@Override
	public abstract void serialize(ServerMessage response);

}
