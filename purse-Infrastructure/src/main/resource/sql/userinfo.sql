--用户余额表
create table user_balance (
    user_id int(11) COMMENT '用户Id',
    price decimal(10,4) not null default 0.0000 comment '钱包余额',
    create_time DATETIME not null comment '创建时间',
    update_time DATETIME not null comment '修改时间',
    creator int(11) comment '创建人',
    modifier int(11) comment '修改人',
    del_flag boolean default false comment '是否删除',
    PRIMARY KEY (user_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 comment '用户余额表';

into user_balance (user_id, price, create_time, update_time, creator,modifier) values (1,5000,CURTIME(),CURTIME(),1,1);

--余额明细表
create table balance_detail (
                            id int(11) not null AUTO_INCREMENT COMMENT '明细表Id',
                            user_id int(11) not null COMMENT '用户Id',
                            amount decimal(10,4) not null comment '金额',
                            type varchar(1024) not null comment '明细类型：支出、充值',
                            order_id varchar(1024) not null comment '关联的订单Id',
                            create_time DATETIME not null comment '创建时间',
                            update_time DATETIME not null comment '修改时间',
                            creator int(11) comment '创建人',
                            modifier int(11) comment '修改人',
                            del_flag boolean not null default false comment '是否删除',
                            PRIMARY KEY (id)
                            INDEX(user_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 comment '余额明细表';
