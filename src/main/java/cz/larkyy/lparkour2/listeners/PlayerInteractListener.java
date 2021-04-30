package cz.larkyy.lparkour2.listeners;

import cz.larkyy.lparkour2.LParkour2;
import cz.larkyy.lparkour2.handlers.LevelsHandler;
import cz.larkyy.lparkour2.handlers.MessageHandler;
import cz.larkyy.lparkour2.handlers.PlayerHandler;
import cz.larkyy.lparkour2.inventories.CategoryMenu;
import cz.larkyy.lparkour2.inventories.LocationCheckpointsMenu;
import cz.larkyy.lparkour2.inventories.MapCategorySelectionMenu;
import cz.larkyy.lparkour2.inventories.MapCreatorsMenu;
import cz.larkyy.lparkour2.objects.CheckpointObj;
import cz.larkyy.lparkour2.objects.EditingPlayer;
import cz.larkyy.lparkour2.objects.LevelObj;
import cz.larkyy.lparkour2.objects.PlayerObj;
import cz.larkyy.lparkour2.utils.Utils;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class PlayerInteractListener implements Listener {


    @EventHandler
    public void onInteract(PlayerInteractEvent e) {

        Player p = e.getPlayer();

        if (e.getAction().equals(Action.PHYSICAL))
            return;

        if (e.getHand()==null) {
            return;
        }

        if (e.getHand().equals(EquipmentSlot.OFF_HAND))
            return;

        if (getMain().getListenerRegisters().getPlayersDropped().contains(p.getUniqueId()))
            return;

        ItemStack is = p.getInventory().getItemInMainHand();

        if (is.getItemMeta()!=null) {

            String lname = is.getItemMeta().getLocalizedName();

            if (getPlayerHandler().isEditingPlayer(p)) {
                e.setCancelled(true);

                Block b = e.getClickedBlock();
                EditingPlayer player = getPlayerHandler().getEditingPlayer(p);

                if (e.getAction().equals(Action.LEFT_CLICK_AIR) || e.getAction().equals(Action.LEFT_CLICK_BLOCK)) {

                    if (lname.equals("checkpoint location")) {
                        p.openInventory(new LocationCheckpointsMenu(0, player).getInventory());
                        return;
                    }

                    if (lname.equals("checkpoint sign") && e.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
                        if (isntSign(b)) {
                            MessageHandler.NOT_SIGN.sendMsg(p);
                            return;
                        }
                        CheckpointObj checkpoint = new CheckpointObj(CheckpointObj.CheckType.SIGN, b.getLocation());

                        if (!player.getCheckpoints().containsKey(checkpoint.getLocation())) {
                            MessageHandler.NO_CHECKPOINT.sendMsg(p);
                            return;
                        }

                        player.removeCheckpoint(checkpoint);
                        MessageHandler.CHECKPOINT_REMOVE.sendMsg(p);

                        return;
                    }
                } else {

                    if (lname.equals("checkpoint location")) {
                        CheckpointObj checkpoint = new CheckpointObj(CheckpointObj.CheckType.LOCATION, p.getLocation().clone());

                        if (player.isCheckPoint(CheckpointObj.CheckType.LOCATION, checkpoint.getLocation())) {
                            MessageHandler.ALREADY_CHECKPOINT.sendMsg(p);
                            return;
                        }

                        player.addCheckpoint(checkpoint);
                        MessageHandler.CHECKPOINT_ADD.sendMsg(p);
                        return;
                    }

                    if (lname.equals("checkpoint sign") && e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                        if (isntSign(b)) {
                            MessageHandler.NOT_SIGN.sendMsg(p);
                            return;
                        }
                        CheckpointObj checkpoint = new CheckpointObj(CheckpointObj.CheckType.SIGN, b.getLocation());

                        if (player.isCheckPoint(CheckpointObj.CheckType.SIGN, checkpoint.getLocation())) {
                            MessageHandler.ALREADY_CHECKPOINT.sendMsg(p);
                            return;
                        }

                        player.addCheckpoint(checkpoint);
                        MessageHandler.CHECKPOINT_ADD.sendMsg(p);
                        editSign(b);

                        return;
                    }
                }

                if (lname.equals("category")) {
                    p.openInventory(new MapCategorySelectionMenu().getInventory());
                    return;
                }

                if (lname.equals("start")) {
                    player.setStart(p.getLocation());
                    MessageHandler.START_SET.sendMsg(p);
                    return;
                }

                if (lname.equals("end")) {
                    player.setEnd(Utils.blockLocation(p.getLocation().clone()));
                    MessageHandler.END_SET.sendMsg(p);
                    return;
                }

                if (lname.equals("creators")) {
                    p.openInventory(new MapCreatorsMenu(player).getInventory());
                    return;
                }

                if (lname.equals("finish")) {
                    if (player.getDifficulty() == null) {
                        MessageHandler.MUST_SET_CATEGORY.sendMsg(p);
                        return;
                    }
                    if (player.getStart() == null) {
                        MessageHandler.MUST_SET_START_LOCATION.sendMsg(p);
                        return;
                    }
                    if (player.getEnd() == null) {
                        MessageHandler.MUST_SET_END_LOCATION.sendMsg(p);
                        return;
                    }

                    p.getInventory().setContents(player.getInventory());
                    getPlayerHandler().removeEditingPlayer(player);

                    if (player.getLevel() == null) {
                        getLevelsHandler().addLevelByPlayer(p, player);
                        return;
                    }
                    getLevelsHandler().editLevelByPlayer(p, player);
                    return;
                }
            }
            if (lname.equals("levels")) {
                p.openInventory(new CategoryMenu().getInventory());
                return;
            }
            if (lname.equals("checkpoint")) {
                PlayerObj player = getPlayerHandler().getPlayer(p.getUniqueId());
                if (player.getActualLevel() == null) {
                    p.sendMessage("You are not in any level!");
                } else
                    p.teleport(player.getCpLoc());
                return;
            }
        }
        Block b = e.getClickedBlock();
        if (b==null) return;

        if (!isntSign(b)) {
            PlayerObj player = getPlayerHandler().getPlayer(p.getUniqueId());
            if (!player.isInLevel()) return;
            LevelObj level = player.getActualLevel();
            for (Map.Entry<Location,CheckpointObj> pair : level.getCheckpoints().entrySet()) {
                if (pair.getValue().getType().equals(CheckpointObj.CheckType.SIGN)) {
                    player.setCheckpoint(pair.getValue());
                    player.setCploc(p.getLocation());
                    p.sendMessage("You have reached the checkpoint!");
                    return;
                }
            }
        }
    }

    private LevelsHandler getLevelsHandler() {
        return getMain().getLevelsHandler();
    }

    private void editSign(Block b) {
        Sign sign = (Sign) b.getState();

        sign.setLine(0, Utils.format(""));
        sign.setLine(1, Utils.format("&3[Checkpoint]"));
        sign.setLine(2, Utils.format("&fClick to set"));
        sign.setLine(3, Utils.format(""));

        sign.update();
    }

    private LParkour2 getMain() {
        return LParkour2.getMain();
    }

    private boolean isntSign(Block b) {
        return !b.getType().toString().contains("SIGN");
    }

    private PlayerHandler getPlayerHandler(){
        return getMain().getPlayerHandler();
    }
}
