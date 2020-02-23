package com.sirarchlinux.runelite.polybar;

import com.google.inject.Provides;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.Player;
import net.runelite.api.events.AnimationChanged;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;

import javax.inject.Inject;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static net.runelite.api.AnimationID.*;

@Slf4j
@PluginDescriptor(
        name = "Polybar Integration"
)
public class PolybarIntegrationPlugin extends Plugin {
    @Getter
    private State globalState = State.IDLE;

    @Inject
    private Client client;

    @Inject
    private PolybarIntegrationConfig config;

    @Inject
    private ScheduledExecutorService executorService;

    @Override
    protected void startUp() {
        writeState(State.IDLE);
        notifyPolybar();
    }

    @Override
    protected void shutDown() {
        writeState(State.IDLE);
        notifyPolybar();
    }

    @Subscribe
    public void onAnimationChanged(final AnimationChanged event) {
        Player localPlayer = client.getLocalPlayer();

        if (localPlayer == null || event.getActor() != localPlayer) {
            return;
        }

        int animation = localPlayer.getAnimation();

        State newState;
        switch (animation) {
            case WOODCUTTING_BRONZE:
            case WOODCUTTING_IRON:
            case WOODCUTTING_STEEL:
            case WOODCUTTING_BLACK:
            case WOODCUTTING_MITHRIL:
            case WOODCUTTING_ADAMANT:
            case WOODCUTTING_RUNE:
            case WOODCUTTING_DRAGON:
            case WOODCUTTING_INFERNAL:
            case WOODCUTTING_3A_AXE:
            case WOODCUTTING_CRYSTAL:
                newState = State.WCING;
                break;
            case MINING_BRONZE_PICKAXE:
            case MINING_IRON_PICKAXE:
            case MINING_STEEL_PICKAXE:
            case MINING_BLACK_PICKAXE:
            case MINING_MITHRIL_PICKAXE:
            case MINING_ADAMANT_PICKAXE:
            case MINING_RUNE_PICKAXE:
            case MINING_DRAGON_PICKAXE:
            case MINING_DRAGON_PICKAXE_UPGRADED:
            case MINING_DRAGON_PICKAXE_OR:
            case MINING_INFERNAL_PICKAXE:
            case MINING_3A_PICKAXE:
            case MINING_CRYSTAL_PICKAXE:
            case MINING_MOTHERLODE_BRONZE:
            case MINING_MOTHERLODE_IRON:
            case MINING_MOTHERLODE_STEEL:
            case MINING_MOTHERLODE_BLACK:
            case MINING_MOTHERLODE_MITHRIL:
            case MINING_MOTHERLODE_ADAMANT:
            case MINING_MOTHERLODE_RUNE:
            case MINING_MOTHERLODE_DRAGON:
            case MINING_MOTHERLODE_DRAGON_UPGRADED:
            case MINING_MOTHERLODE_DRAGON_OR:
            case MINING_MOTHERLODE_INFERNAL:
            case MINING_MOTHERLODE_3A:
            case MINING_MOTHERLODE_CRYSTAL:
            case 643:
                newState = State.MINING;
                break;
            case 713:
            case 712:
                newState = State.ALCH;
                break;
            case 833:
            case 828:
                newState = State.CLIMB;
                break;
            case SMITHING_ANVIL:
                newState = State.SMITH;
                break;
            case SMITHING_SMELTING:
            case SMITHING_CANNONBALL:
                newState = State.SMELT;
                break;
            case 3705:
                newState = State.PRAY;
                break;
            case FLETCHING_BOW_CUTTING:
            case FLETCHING_ATTACH_FEATHERS_TO_ARROWSHAFT:
            case FLETCHING_ATTACH_HEADS:
            case FLETCHING_STRING_MAGIC_LONGBOW:
            case FLETCHING_STRING_MAGIC_SHORTBOW:
            case FLETCHING_STRING_MAPLE_LONGBOW:
            case FLETCHING_STRING_MAPLE_SHORTBOW:
            case FLETCHING_STRING_NORMAL_LONGBOW:
            case FLETCHING_STRING_NORMAL_SHORTBOW:
            case FLETCHING_STRING_OAK_LONGBOW:
            case FLETCHING_STRING_OAK_SHORTBOW:
            case FLETCHING_STRING_WILLOW_LONGBOW:
            case FLETCHING_STRING_WILLOW_SHORTBOW:
            case FLETCHING_STRING_YEW_LONGBOW:
            case FLETCHING_STRING_YEW_SHORTBOW:
                newState = State.FLETCH;
                break;
            case COOKING_FIRE:
            case COOKING_RANGE:
            case COOKING_WINE:
                newState = State.COOK;
                break;
            case 397:
                newState = State.STUN;
                break;
            case GEM_CUTTING_AMETHYST:
            case GEM_CUTTING_DIAMOND:
            case GEM_CUTTING_EMERALD:
            case GEM_CUTTING_JADE:
            case GEM_CUTTING_OPAL:
            case GEM_CUTTING_REDTOPAZ:
            case GEM_CUTTING_RUBY:
            case GEM_CUTTING_SAPPHIRE:
                newState = State.GEM;
                break;
            case FISHING_POLE_CAST:
            case FISHING_BARBTAIL_HARPOON:
            case FISHING_BAREHAND:
            case FISHING_BIG_NET:
            case FISHING_CAGE:
            case FISHING_CRYSTAL_HARPOON:
            case FISHING_DRAGON_HARPOON:
            case FISHING_HARPOON:
            case FISHING_INFERNAL_HARPOON:
            case FISHING_KARAMBWAN:
            case FISHING_NET:
            case FISHING_OILY_ROD:
                newState = State.FISH;
                break;
            case BOOK_HOME_TELEPORT_1:
            case BOOK_HOME_TELEPORT_2:
            case BOOK_HOME_TELEPORT_3:
            case BOOK_HOME_TELEPORT_4:
            case BOOK_HOME_TELEPORT_5:
            case COW_HOME_TELEPORT_1:
            case COW_HOME_TELEPORT_2:
            case COW_HOME_TELEPORT_3:
            case COW_HOME_TELEPORT_4:
            case COW_HOME_TELEPORT_5:
            case COW_HOME_TELEPORT_6:
                newState = State.TELE;
                break;
            case FIREMAKING:
                newState = State.FIRE;
                break;
            case 881:
            case 832:
                newState = State.STEAL;
                break;
            case 714:
                newState = State.MTELE;
                break;
            case FARMING_HARVEST_ALLOTMENT:
            case FARMING_HARVEST_BUSH:
            case FARMING_HARVEST_FLOWER:
            case FARMING_HARVEST_FRUIT_TREE:
            case FARMING_HARVEST_HERB:
            case FARMING_MIX_ULTRACOMPOST:
            case FARMING_CURE_WITH_POTION:
            case FARMING_PLANT_SEED:
            case FARMING_USE_COMPOST:
                newState = State.FARM;
                break;
            case HERBLORE_MAKE_TAR:
            case HERBLORE_PESTLE_AND_MORTAR:
            case HERBLORE_POTIONMAKING:
                newState = State.HERB;
                break;
            case HUNTER_CHECK_BIRD_SNARE:
            case HUNTER_CHECK_BOX_TRAP:
                newState = State.CHUNT;
                break;
            case HUNTER_LAY_BOXTRAP_BIRDSNARE:
            case HUNTER_LAY_MANIACAL_MONKEY_BOULDER_TRAP:
            case HUNTER_LAY_NETTRAP:
                newState = State.LHUNT;
                break;
            case SAND_COLLECTION:
                newState = State.SAND;
                break;
            case MAGIC_MAKE_TABLET:
            case HOME_MAKE_TABLET:
                newState = State.TABMK;
                break;
            case CONSTRUCTION:
                newState = State.CONSTR;
                break;
            case CRAFTING_BATTLESTAVES:
                newState = State.CRFTBS;
                break;
            case CRAFTING_GLASSBLOWING:
            case CRAFTING_LEATHER:
            case CRAFTING_POTTERS_WHEEL:
            case CRAFTING_POTTERY_OVEN:
            case CRAFTING_SPINNING:
                newState = State.CRAFT;
                break;
            case MAGIC_CHARGING_ORBS:
                newState = State.CHORB;
                break;
            case MAGIC_ARCEUUS_RESURRECT_CROPS:
            case MAGIC_LUNAR_CURE_PLANT:
                newState = State.BCRPS;
                break;
            case MAGIC_LUNAR_PLANK_MAKE:
                newState = State.PLANK;
                break;
            case MAGIC_LUNAR_STRING_JEWELRY:
                newState = State.STRNG;
                break;
            case MAGIC_ENCHANTING_AMULET_1:
            case MAGIC_ENCHANTING_AMULET_2:
            case MAGIC_ENCHANTING_AMULET_3:
            case MAGIC_ENCHANTING_JEWELRY:
                newState = State.ENCHJWL;
                break;
            default:
                newState = State.IDLE;
                break;
        }

        if (globalState != newState) {
            globalState = newState;
            writeState(newState);
            notifyPolybar();
        }
    }

    private void writeState(State state) {
        try {
            PrintWriter writer = new PrintWriter("/tmp/rl_state", "UTF-8");
            writer.println(state.getMessage());
            writer.close();
        } catch (IOException e) {
            log.error("Failed to write to file /tmp/rl_state");
        }
    }

    private void notifyPolybar() {
        final List<String> commands = new ArrayList<>();
        commands.add("polybar-msg");
        commands.add("hook");
        commands.add(config.module());
        commands.add(String.valueOf(config.hook()));
        executorService.submit(() ->
        {
            try {
                Process notificationProcess = new ProcessBuilder(commands.toArray(new String[0]))
                        .redirectErrorStream(true)
                        .start();

                boolean exited = notificationProcess.waitFor(500, TimeUnit.MILLISECONDS);
                if (exited && notificationProcess.exitValue() == 0) {
                    log.debug("polybar-msg command executed successfully");
                    return;
                }
            } catch (IOException | InterruptedException ex) {
                log.error("Execution of polybar-msg command threw exception", ex);
            }

            log.debug("polybar-msg returned with error");
        });
    }

    @Provides
    PolybarIntegrationConfig provideConfig(ConfigManager configManager) {
        return configManager.getConfig(PolybarIntegrationConfig.class);
    }
}
