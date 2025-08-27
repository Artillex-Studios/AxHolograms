package com.artillexstudios.axholograms.command;

import com.artillexstudios.axapi.utils.Location;
import com.artillexstudios.axapi.utils.MessageUtils;
import com.artillexstudios.axapi.utils.PaperUtils;
import com.artillexstudios.axholograms.AxHologramsPlugin;
import com.artillexstudios.axholograms.api.AxHologramsAPI;
import com.artillexstudios.axholograms.api.holograms.Hologram;
import com.artillexstudios.axholograms.api.holograms.type.HologramType;
import com.artillexstudios.axholograms.command.argument.ArgumentTypes;
import com.artillexstudios.axholograms.config.Language;
import com.artillexstudios.axholograms.hologram.HologramPage;
import dev.jorel.commandapi.CommandAPI;
import dev.jorel.commandapi.CommandAPIBukkitConfig;
import dev.jorel.commandapi.CommandTree;
import dev.jorel.commandapi.arguments.LiteralArgument;
import dev.jorel.commandapi.arguments.MultiLiteralArgument;
import dev.jorel.commandapi.arguments.StringArgument;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;

public class AxHologramsCommand {

    public static void onLoad(AxHologramsPlugin plugin) {
        CommandAPI.onLoad(new CommandAPIBukkitConfig(plugin)
                .setNamespace("axholograms")
                .skipReloadDatapacks(true)
        );
    }

    public static void onEnable() {
        CommandAPI.onEnable();
    }

    public static void onDisable() {
        CommandAPI.onDisable();
    }

    @SuppressWarnings("DataFlowIssue")
    public static void register() {
        new CommandTree("holograms")
                .withAliases("holograms", "axholograms", "holo", "axholo")
                .then(new LiteralArgument("create")
                        .withPermission("axholograms.command.create")
                        .then(new StringArgument("name")
                                .then(ArgumentTypes.hologramType("type")
                                        .executesPlayer((sender, args) -> {
                                            HologramType type = args.getByClass("type", HologramType.class);
                                            String name = args.getByClass("name", String.class);

                                            if (AxHologramsAPI.getInstance().getRegistry().getByName(name) != null) {
                                                MessageUtils.sendMessage(sender, Language.prefix, Language.hologramAlreadyExists, Placeholder.unparsed("name", name));
                                                return;
                                            }

                                            Hologram hologram = AxHologramsAPI.getInstance().createSaveableHologram(name, Location.create(sender.getLocation()));
                                            hologram.addPage(new HologramPage(type.createDefaultPageData()));
                                            hologram.loadWithWorld();
                                            hologram.save();
                                            AxHologramsAPI.getInstance().getRegistry().register(hologram);
                                            MessageUtils.sendMessage(sender, Language.prefix, Language.successfullyCreatedHologram, Placeholder.unparsed("name", name));
                                        })
                                )
                        )
                )
                .then(new MultiLiteralArgument("delete", "remove")
                        .withPermission("axholograms.command.delete")
                        .then(ArgumentTypes.hologram("hologram")
                                .executes((sender, args) -> {
                                    Hologram hologram = args.getByClass("hologram", Hologram.class);
                                    hologram.delete();
                                    MessageUtils.sendMessage(sender, Language.prefix, Language.successfullyDeletedHologram, Placeholder.unparsed("name", hologram.getName()));
                                })
                        )
                )
                .then(new LiteralArgument("teleport")
                        .withPermission("axholograms.command.teleport")
                        .then(ArgumentTypes.hologram("hologram")
                                .executesPlayer((sender, args) -> {
                                    Hologram hologram = args.getByClass("hologram", Hologram.class);
                                    PaperUtils.teleportAsync(sender, hologram.getLocation().toBukkit()).thenRun(() -> {
                                        MessageUtils.sendMessage(sender, Language.prefix, Language.successfullyTeleportedToHologram, Placeholder.unparsed("name", hologram.getName()));
                                    });
                                })
                        )
                )
                .then(new MultiLiteralArgument("move", "teleporthere", "tphere")
                        .withPermission("axholograms.command.move")
                        .then(ArgumentTypes.hologram("hologram")
                                .executesPlayer((sender, args) -> {
                                    Hologram hologram = args.getByClass("hologram", Hologram.class);
                                    hologram.setLocation(Location.create(sender.getLocation()));
                                    MessageUtils.sendMessage(sender, Language.prefix, Language.successfullyTeleportedHologram, Placeholder.unparsed("name", hologram.getName()));
                                })
                        )
                )
                .then(new LiteralArgument("edit")
                        .withPermission("axholograms.command.edit")
                        .then(ArgumentTypes.hologram("hologram"))
                )
                .then(new LiteralArgument("center")
                        .withPermission("axholograms.command.center")
                        .then(ArgumentTypes.hologram("hologram")
                                .executesPlayer((sender, args) -> {
                                    Hologram hologram = args.getByClass("hologram", Hologram.class);
                                    hologram.setLocation(hologram.getLocation().copy().toCenter());
                                    MessageUtils.sendMessage(sender, Language.prefix, Language.successfullyCenteredHologram, Placeholder.unparsed("name", hologram.getName()));
                                })
                        )
                )
                .register();
    }
}
