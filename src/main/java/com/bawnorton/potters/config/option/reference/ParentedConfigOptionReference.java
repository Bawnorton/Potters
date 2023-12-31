package com.bawnorton.potters.config.option.reference;

import com.bawnorton.potters.config.option.OptionType;

import java.lang.reflect.Field;

public class ParentedConfigOptionReference extends ConfigOptionReference {
    private final ConfigOptionReference parent;

    private ParentedConfigOptionReference(ConfigOptionReference parent, Object instance, Field field) {
        super(instance, field);
        this.parent = parent;
    }

    public static ParentedConfigOptionReference of(ConfigOptionReference parent, Object instance, Field field) {
        return new ParentedConfigOptionReference(parent, instance, field);
    }

    @Override
    public String getFormattedName() {
        return getParent().getFormattedName() + "." + super.getFormattedName();
    }

    @Override
    public boolean isOf(OptionType type) {
        if (getOptionType() == OptionType.INHERIT) return parent.isOf(type);
        return super.isOf(type);
    }

    public ConfigOptionReference getParent() {
        return parent;
    }
}
