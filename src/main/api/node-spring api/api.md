>1:新建账户 eth

1.请求地址: /eth/createAccount
2.请求方式: get
3.请求参数: UID  用户唯一UID
4.请求返回:

code\status | 0 | 1 |  2
---|---|---|---
0 | 创建失败
1 | 请求成功
2 | 系统错误


```
{
    "code":,		  0:fail 1:success 2:error
	"status":,		  code的状态描述（表格）
	"data":{

	}
}
```

>2:新建账户 eos

1.请求地址: /eos/createAccount
2.请求方式: post
3.请求参数: UID  用户唯一UID
4.请求返回:

code\status | 0 | 1 |  2
---|---|---|---
0 | 创建失败
1 | 请求成功
2 | 系统错误


```
{
    "code":,		  0:fail 1:success 2:error
	"status":,		  code的状态描述（表格）
	"data":{

	}
}
```

>3:转账 eth

1.请求地址: /eth/transfer
2.请求方式: post
3.请求参数: UID  用户唯一UID, fromAddress 转出地址, toAddress 转到地址, amount 数量, type 转账类型
4.请求返回:

code\status | 0 | 1 |  2
---|---|---|---
0 | 创建失败 | 余额不足
1 | 请求成功
2 | 系统错误


```
{
    "code":,		  0:fail 1:success 2:error
	"status":,		  code的状态描述（表格）
	"data":{

	}
}
```

>4:转账 eos

1.请求地址: /eos/transfer
2.请求方式: post
3.请求参数: UID  用户唯一UID, fromAccount 转出地址, toAccount 转到地址, amount 数量, type 转账类型
4.请求返回:

code\status | 0 | 1 |  2
---|---|---|---
0 | 创建失败 | 余额不足
1 | 请求成功
2 | 系统错误


```
{
    "code":,		  0:fail 1:success 2:error
	"status":,		  code的状态描述（表格）
	"data":{

	}
}
```

>5:质押net和cpu eos

1.请求地址: /eos/trxNetCpu
2.请求方式: post
3.请求参数: UID  用户唯一UID, cpuAmount cpu数量, netAmount net数量, actionType 类型 (0:抵押 1:赎回 )
4.请求返回:

code\status | 0 | 1 |  2
---|---|---|---
0 | 创建失败 | 余额不足
1 | 请求成功
2 | 系统错误


```
{
    "code":,		  0:fail 1:success 2:error
	"status":,		  code的状态描述（表格）
	"data":{

	}
}
```

>6:买卖ram eos

1.请求地址: /eos/trxRam
2.请求方式: post
3.请求参数: UID  用户唯一UID, RamAmount ram数量, actionType 类型 (0:买 1:卖 )
4.请求返回:

code\status | 0 | 1 |  2
---|---|---|---
0 | 创建失败 | 余额不足
1 | 请求成功
2 | 系统错误


```
{
    "code":,		  0:fail 1:success 2:error
	"status":,		  code的状态描述（表格）
	"data":{

	}
}
```

>7:查询账户信息

1.请求地址: /eos/getAccount
2.请求方式: get
3.请求参数: UID  用户唯一UID
4.请求返回:

code\status | 0 | 1 |  2
---|---|---|---
0 | 查询失败 
1 | 请求成功
2 | 系统错误


```
{
    "code":,		  0:fail 1:success 2:error
	"status":,		  code的状态描述（表格）
	"data":{
		"accountData":{
			eos 区块链json包
		}
	}
}
```