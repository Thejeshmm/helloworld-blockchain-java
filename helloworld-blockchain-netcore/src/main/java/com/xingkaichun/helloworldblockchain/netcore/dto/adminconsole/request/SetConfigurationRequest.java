package com.xingkaichun.helloworldblockchain.netcore.dto.adminconsole.request;

import com.xingkaichun.helloworldblockchain.netcore.dto.adminconsole.ConfigurationDto;

/**
 *
 * @author 邢开春 xingkaichun@qq.com
 */
public class SetConfigurationRequest {

    private ConfigurationDto configurationDto;




    //region get set
    public ConfigurationDto getConfigurationDto() {
        return configurationDto;
    }

    public void setConfigurationDto(ConfigurationDto configurationDto) {
        this.configurationDto = configurationDto;
    }
    //endregion
}