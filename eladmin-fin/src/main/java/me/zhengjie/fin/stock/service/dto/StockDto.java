/*
 *  Copyright 2019-2020 Zheng Jie
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package me.zhengjie.fin.stock.service.dto;

import lombok.Getter;
import lombok.Setter;
import me.zhengjie.base.BaseDTO;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * @author zhangjc
 * @date 2023-02-15
 */
@Getter
@Setter
public class StockDto extends BaseDTO implements Serializable {

	private Integer id;

	private String stockCode;

	private String stockName;

	private Float holdAmount;

	private Float holdShare;

	private Float currentPrice;

	private Timestamp priceTime;

	private String createBy;

	private Timestamp createTime;

	private String updateBy;

	private Timestamp updateTime;

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		StockDto that = (StockDto) o;
		return Objects.equals(id, that.id) &&
				Objects.equals(stockCode, that.stockCode);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, stockCode);
	}
}
