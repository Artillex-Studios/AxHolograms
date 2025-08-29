package com.artillexstudios.axholograms.command;

import com.artillexstudios.axapi.packetentity.meta.entity.DisplayMeta;
import com.artillexstudios.axapi.utils.Location;
import com.artillexstudios.axapi.utils.MessageUtils;
import com.artillexstudios.axapi.utils.PaperUtils;
import com.artillexstudios.axapi.utils.Vector3f;
import com.artillexstudios.axholograms.AxHologramsPlugin;
import com.artillexstudios.axholograms.api.AxHologramsAPI;
import com.artillexstudios.axholograms.api.holograms.Hologram;
import com.artillexstudios.axholograms.api.holograms.type.HologramType;
import com.artillexstudios.axholograms.command.argument.ArgumentTypes;
import com.artillexstudios.axholograms.config.Language;
import com.artillexstudios.axholograms.data.DisplayEntityHologramPageData;
import com.artillexstudios.axholograms.data.TextHologramPageData;
import com.artillexstudios.axholograms.hologram.HologramPage;
import dev.jorel.commandapi.CommandAPI;
import dev.jorel.commandapi.CommandAPIBukkitConfig;
import dev.jorel.commandapi.CommandTree;
import dev.jorel.commandapi.arguments.FloatArgument;
import dev.jorel.commandapi.arguments.GreedyStringArgument;
import dev.jorel.commandapi.arguments.IntegerArgument;
import dev.jorel.commandapi.arguments.LiteralArgument;
import dev.jorel.commandapi.arguments.MultiLiteralArgument;
import dev.jorel.commandapi.arguments.StringArgument;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;

import java.util.Arrays;
import java.util.Locale;

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
                        .then(ArgumentTypes.hologram("hologram")
                                .then(new LiteralArgument("line")
                                        .then(new LiteralArgument("add")
                                                .then(new IntegerArgument("page", 1)
                                                        .then(new GreedyStringArgument("content")
                                                                .executes((sender, args) -> {
                                                                    Hologram hologram = args.getByClass("hologram", Hologram.class);
                                                                    int pageNum = args.getByClass("page", Integer.class) - 1;
                                                                    if (pageNum > hologram.getPages().size() - 1) {
                                                                        sender.sendMessage("Index out of bounds!");
                                                                        return;
                                                                    }

                                                                    var page = hologram.getPages().get(pageNum);
                                                                    if (!(page.getData() instanceof TextHologramPageData data)) {
                                                                        return;
                                                                    }
                                                                    String contentArgument = args.getByClass("content", String.class);

                                                                    String content = data.getContent() + "\n<br>" + contentArgument;
                                                                    data.setContent(content);
                                                                    hologram.save();
                                                                })
                                                        )
                                                )
                                                .then(new GreedyStringArgument("content")
                                                        .executes((sender, args) -> {
                                                            Hologram hologram = args.getByClass("hologram", Hologram.class);
                                                            var page = hologram.getPages().getFirst();
                                                            if (!(page.getData() instanceof TextHologramPageData data)) {
                                                                return;
                                                            }
                                                            String contentArgument = args.getByClass("content", String.class);

                                                            String content = data.getContent() + "\n<br>" + contentArgument;
                                                            data.setContent(content);
                                                            hologram.save();
                                                        })
                                                )
                                        )
                                        .then(new LiteralArgument("remove")
                                                .then(new IntegerArgument("line")
                                                        .executes((sender, args) -> {

                                                        })
                                                )
                                        )
                                        .then(new LiteralArgument("set")
                                                .then(new IntegerArgument("line")
                                                        .then(new GreedyStringArgument("content")
                                                                .executes((sender, args) -> {

                                                                })
                                                        )
                                                )
                                        )
                                        .then(new LiteralArgument("insert")
                                                .then(new LiteralArgument("before")
                                                        .then(new IntegerArgument("line")
                                                                .then(new GreedyStringArgument("content")

                                                                )
                                                        )

                                                )
                                                .then(new LiteralArgument("after")

                                                )
                                                .then(new IntegerArgument("line")
                                                        .executes((sender, args) -> {

                                                        })
                                                )
                                        )
                                )
                                .then(new LiteralArgument("translation")
                                        .then(new FloatArgument("x")
                                                .then(new FloatArgument("y")
                                                        .then(new FloatArgument("z")
                                                                .executes((sender, args) -> {
                                                                    Hologram hologram = args.getByClass("hologram", Hologram.class);
                                                                    for (com.artillexstudios.axholograms.api.holograms.HologramPage page : hologram.getPages()) {
                                                                        if (!(page.getData() instanceof DisplayEntityHologramPageData data)) {
                                                                            return;
                                                                        }
                                                                        float x = args.getByClass("x", Float.class);
                                                                        float y = args.getByClass("y", Float.class);
                                                                        float z = args.getByClass("z", Float.class);

                                                                        data.setTranslation(new Vector3f(x, y, z));
                                                                    }
                                                                    hologram.save();
                                                                })
                                                        )
                                                )
                                        )
                                )
                                .then(new LiteralArgument("scale")
                                        .then(new FloatArgument("x")
                                                .then(new FloatArgument("y")
                                                        .then(new FloatArgument("z")
                                                                .executes((sender, args) -> {
                                                                    Hologram hologram = args.getByClass("hologram", Hologram.class);
                                                                    for (com.artillexstudios.axholograms.api.holograms.HologramPage page : hologram.getPages()) {
                                                                        if (!(page.getData() instanceof DisplayEntityHologramPageData data)) {
                                                                            return;
                                                                        }
                                                                        float x = args.getByClass("x", Float.class);
                                                                        float y = args.getByClass("y", Float.class);
                                                                        float z = args.getByClass("z", Float.class);

                                                                        data.setScale(new Vector3f(x, y, z));
                                                                    }
                                                                    hologram.save();
                                                                })
                                                        )
                                                )
                                        )
                                ).then(new LiteralArgument("billboard")
                                        .then(new MultiLiteralArgument("constrain", Arrays.stream(DisplayMeta.BillboardConstrain.values())
                                                        .map(DisplayMeta.BillboardConstrain::name)
                                                        .map(line -> line.toLowerCase(Locale.ENGLISH))
                                                        .toList()
                                                        .toArray(new String[0])
                                                )
                                                .executes((sender, args) -> {
                                                    Hologram hologram = args.getByClass("hologram", Hologram.class);
                                                    String constrainName = args.getByClass("constrain", String.class);
                                                    DisplayMeta.BillboardConstrain constrain = DisplayMeta.BillboardConstrain.valueOf(constrainName.toUpperCase(Locale.ENGLISH));
                                                    for (com.artillexstudios.axholograms.api.holograms.HologramPage page : hologram.getPages()) {
                                                        if (!(page.getData() instanceof DisplayEntityHologramPageData data)) {
                                                            return;
                                                        }

                                                        data.setBillboardConstrain(constrain);
                                                    }
                                                    hologram.save();
                                                })
                                        )
                                )
                        )
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
