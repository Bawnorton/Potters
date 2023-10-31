package com.bawnorton.potters.datagen.model;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.data.client.Model;
import net.minecraft.data.client.TextureKey;
import net.minecraft.util.Identifier;

import java.util.Map;
import java.util.Optional;

public class PottersBlockModel extends Model {
    public PottersBlockModel() {
        super(Optional.of(new Identifier("builtin/entity")), Optional.empty(), TextureKey.PARTICLE);
    }

    @Override
    public JsonObject createJson(Identifier id, Map<TextureKey, Identifier> textures) {
        JsonObject initial = super.createJson(id, textures);
        JsonObject display = new JsonObject();
        constructTransformation(display, ModelTransformationMode.THIRD_PERSON_RIGHT_HAND, new float[]{0, 90, 0, 0, 2, 0.5f, 0.375f, 0.375f, 0.375f});
        constructTransformation(display, ModelTransformationMode.FIRST_PERSON_RIGHT_HAND, new float[]{0, 90, 0, 0, 0, 0, 0.375f, 0.375f, 0.375f});
        constructTransformation(display, ModelTransformationMode.GUI, new float[]{30, 45, 0, 0, 0, 0, 0.60f, 0.60f, 0.60f});
        constructTransformation(display, ModelTransformationMode.GROUND, new float[]{0, 0, 0, 0, 1, 0, 0.25f, 0.25f, 0.25f});
        constructTransformation(display, ModelTransformationMode.HEAD, new float[]{0, 180, 0, 0, 16, 0, 1.5f, 1.5f, 1.5f});
        constructTransformation(display, ModelTransformationMode.FIXED, new float[]{0, 180, 0, 0, 0, 0, 0.5f, 0.5f, 0.5f});
        initial.add("display", display);
        initial.addProperty("gui_light", "front");
        return initial;
    }

    private void constructTransformation(JsonObject display, ModelTransformationMode mode, float[] transformations) {
        JsonObject object = new JsonObject();
        float[] rotation = new float[]{transformations[0], transformations[1], transformations[2]};
        float[] translation = new float[]{transformations[3], transformations[4], transformations[5]};
        float[] scale = new float[]{transformations[6], transformations[7], transformations[8]};
        object.add(Transformation.ROTATION.getName(), Transformation.ROTATION.construct(rotation));
        object.add(Transformation.TRANSLATION.getName(), Transformation.TRANSLATION.construct(translation));
        object.add(Transformation.SCALE.getName(), Transformation.SCALE.construct(scale));
        display.add(mode.asString(), object);
    }

    private enum Transformation {
        ROTATION("rotation"), TRANSLATION("translation"), SCALE("scale");

        private final String name;

        Transformation(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public JsonArray construct(float... values) {
            JsonArray array = new JsonArray();
            for (float value : values) {
                array.add(value);
            }
            return array;
        }
    }
}
