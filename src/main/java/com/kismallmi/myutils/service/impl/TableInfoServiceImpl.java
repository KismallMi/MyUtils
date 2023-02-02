package com.kismallmi.myutils.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kismallmi.myutils.entity.TableInfo;
import com.kismallmi.myutils.service.TableInfoService;
import com.kismallmi.myutils.mapper.TableInfoMapper;
import org.springframework.stereotype.Service;

/**
 *
 */
@Service
public class TableInfoServiceImpl extends ServiceImpl<TableInfoMapper, TableInfo>
    implements TableInfoService{

}




