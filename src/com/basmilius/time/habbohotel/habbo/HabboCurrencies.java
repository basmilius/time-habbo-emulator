package com.basmilius.time.habbohotel.habbo;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.enums.Currencies;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HabboCurrencies
{

	private final Habbo _habbo;
	private final List<HabboCurrency> _currencies;

	public HabboCurrencies(Habbo _habbo)
	{
		this._habbo = _habbo;
		this._currencies = new ArrayList<>();

		try
		{
			this.loadCurrencies();
		}
		catch (SQLException e)
		{
			Bootstrap.getEngine().getLogging().handleSQLException(e);
		}
	}

	private void loadCurrencies() throws SQLException
	{
		final PreparedStatement statement = Bootstrap.getEngine().getDatabase().prepare("SELECT * FROM users_currencies WHERE user_id = ? ORDER BY currency_id ASC");
		{
			if (statement != null)
			{
				statement.setInt(1, this._habbo.getId());
				final ResultSet result = statement.executeQuery();

				while (result.next())
				{
					this._currencies.add(new HabboCurrency(this._habbo, result));
				}
			}
		}
	}

	public Habbo getHabbo()
	{
		return this._habbo;
	}

	@SuppressWarnings("unused")
	public int getCredits()
	{
		return this._habbo.getSettings().getCredits();
	}

	public int getBalance(int currencyId)
	{
		HabboCurrency currency = this.getCurrency(currencyId);

		if (currency == null)
		{
			currency = this.createCurrency(currencyId, 0);
		}

		return currency.getCurrencyBalance();
	}

	HabboCurrency getCurrency(int id)
	{
		for (HabboCurrency currency : this._currencies)
			if (currency.getCurrencyId() == id)
				return currency;

		return null;
	}

	public List<HabboCurrency> getCurrencies()
	{
		return this._currencies;
	}

	HabboCurrency createCurrency(int currencyId, int currencyBalance)
	{
		if (this.getCurrency(currencyId) != null)
			return this.getCurrency(currencyId);

		HabboCurrency currency = new HabboCurrency(this._habbo, currencyId, currencyBalance);

		this._currencies.add(currency);

		return currency;
	}

	public void updateBalance(int currencyId, int count)
	{
		int max = -1;

		if (currencyId == Currencies.DUCKETS)
			max = Bootstrap.getEngine().getConfig().getInt("hotel.max.duckets", 650);

		HabboCurrency currency = this.getCurrency(currencyId);

		if (currency == null)
			currency = this.createCurrency(currencyId, 0);

		currency.updateBalance(count, max);
	}

}
