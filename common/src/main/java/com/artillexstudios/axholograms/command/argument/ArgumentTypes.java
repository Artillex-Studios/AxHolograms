package com.artillexstudios.axholograms.command.argument;

import com.artillexstudios.axapi.utils.StringUtils;
import com.artillexstudios.axholograms.api.AxHologramsAPI;
import com.artillexstudios.axholograms.api.holograms.Hologram;
import com.artillexstudios.axholograms.api.holograms.type.HologramType;
import com.artillexstudios.axholograms.config.Language;
import dev.jorel.commandapi.arguments.Argument;
import dev.jorel.commandapi.arguments.ArgumentSuggestions;
import dev.jorel.commandapi.arguments.CustomArgument;
import dev.jorel.commandapi.arguments.StringArgument;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;

import java.util.concurrent.CompletableFuture;

public final class ArgumentTypes {

    public static Argument<HologramType<?>> hologramType(String nodeName) {
        return new CustomArgument<HologramType<?>, String>(new StringArgument(nodeName), info -> {
            HologramType<?> type = AxHologramsAPI.getInstance().getTypes().getByString(info.input());
            if (type == null) {
                throw CustomArgument.CustomArgumentException.fromAdventureComponent(StringUtils.format(Language.prefix + Language.invalidHologramType,
                        Placeholder.unparsed("type", info.input()),
                        Placeholder.unparsed("types", String.join(", ", AxHologramsAPI.getInstance().getTypes().names())))
                );
            }
            return type;
        }).replaceSuggestions(ArgumentSuggestions.stringCollectionAsync(info ->
                CompletableFuture.supplyAsync(() ->
                        AxHologramsAPI.getInstance().getTypes().names()
                )
        ));
    }

    public static Argument<Hologram> hologram(String nodeName) {
        return new CustomArgument<>(new StringArgument(nodeName), info -> {
            Hologram hologram = AxHologramsAPI.getInstance().getRegistry().getByName(info.input());
            if (hologram == null) {
                throw CustomArgument.CustomArgumentException.fromAdventureComponent(StringUtils.format(Language.prefix + Language.hologramDoesntExist,
                        Placeholder.unparsed("name", info.input())
                ));
            }

            if (!hologram.isLoaded()) {
                throw CustomArgument.CustomArgumentException.fromAdventureComponent(StringUtils.format(Language.prefix + Language.hologramNotLoaded,
                        Placeholder.unparsed("name", info.input())
                ));
            }

            return hologram;
        }).replaceSuggestions(ArgumentSuggestions.stringCollectionAsync(info ->
                CompletableFuture.supplyAsync(() ->
                        AxHologramsAPI.getInstance().getRegistry().getSavedHolograms().stream().map(Hologram::getName).toList()
                )
        ));
    }
}
