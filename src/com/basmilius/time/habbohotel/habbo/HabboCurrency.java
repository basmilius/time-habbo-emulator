package com.basmilius.time.habbohotel.habbo;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.abstracts.ISave;
import com.basmilius.time.communication.messages.outgoing.users.UpdateCurrencyComposer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HabboCurrency extends ISave
{

	private final Habbo _habbo;
	private int _id;
	private int _currencyId;
	private int _currencyBalance;

	{
		this.setSaveTime(Bootstrap.getEngine().getConfig().getInt("server.thread.save.currency", 30000));
	}

	public HabboCurrency(Habbo _habbo, ResultSet result) throws SQLException
	{
		this._habbo = _habbo;
		this._id = result.getInt("id");
		this._currencyId = result.getInt("currency_id");
		this._currencyBalance = result.getInt("currency_balance");
	}

	public HabboCurrency(Habbo _habbo, int _currencyId, int _currencyBalance)
	{
		this._habbo = _habbo;
		this._currencyId = _currencyId;
		this._currencyBalance = _currencyBalance;

		this.setNeedsInsert(true);
		this.setNeedsUpdate(true);
	}

	public Habbo getHabbo()
	{
		return this._habbo;
	}

	public int getCurrencyId()
	{
		return this._currencyId;
	}

	public int getCurrencyBalance()
	{
		return this._currencyBalance;
	}

	@SuppressWarnings("unused")
	public void updateBalance(int value)
	{
		this.updateBalance(value, -1);
	}

	public void updateBalance(int value, int max)
	{
		int oldValue = this._currencyBalance;
		this._currencyBalance += value;

		if (max > -1)
		{
			if (this._currencyBalance > max)
			{
				this._currencyBalance = max;
			}
		}

		int difference = (this._currencyBalance - oldValue);

		if (difference == 0)
			return;

		if (this._habbo.isOnline())
			this._habbo.getConnection().send(new UpdateCurrencyComposer(this._currencyBalance, difference, this._currencyId));

		this.setNeedsUpdate(true);
	}

	@Override
	public void save() throws SQLException
	{
		if (this.getNeedsInsert())
		{
			final PreparedStatement statement = Bootstrap.getEngine().getDatabase().prepare("INSERT INTO users_currencies (user_id, currency_id, currency_balance) VALUES (?, ?, ?)");
			{
				if (statement != null)
				{
					statement.setInt(1, this._habbo.getId());
					statement.setInt(2, this._currencyId);
					statement.setInt(3, this._currencyBalance);
					statement.execute();

					ResultSet keys = statement.getGeneratedKeys();
					keys.next();
					this._id = keys.getInt(1);
				}
			}
		}
		else if (this.getNeedsUpdate())
		{
			final PreparedStatement statement = Bootstrap.getEngine().getDatabase().prepare("UPDATE users_currencies SET currency_balance = ? WHERE id = ?");
			{
				if (statement != null)
				{
					statement.setInt(1, this._currencyBalance);
					statement.setInt(2, this._id);
					statement.execute();
				}
			}
		}

		this.setNeedsInsert(false);
		this.setNeedsUpdate(false);
	}

}
