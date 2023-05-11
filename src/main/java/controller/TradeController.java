package controller;

import model.*;
import model.building.BuildingType;
import model.building.StockType;
import view.TradeMenu;

import java.util.HashMap;

public class TradeController {
    private final TradeMenu tradeMenu;
    private final Map gameMap;
    private final User currentUser;

    public TradeController() {
        this.tradeMenu = new TradeMenu(this);
        gameMap = GameController.gameMap;
        currentUser = Controller.currentUser;
    }

    public void runTrade() {
        StringBuilder output = new StringBuilder("All Users :");
        for (Kingdom kingdom : gameMap.getPlayers()) {
            output.append(kingdom.getOwner().getUserName()).append("  ---  ");
        }
        System.out.println(output);
        System.out.println(showNotification());
        tradeMenu.run();
    }

    public String newRequest(HashMap<String, String> options) {
        for (String key : options.keySet()) if (options.get(key) == null) return "Please input necessary options!";
        for (String key : options.keySet())
            if (options.get(key).equals("")) return "Illegal value. Please fill the options!";
        User userReceiver;
        try {
            ResourceType.valueOf(options.get("t").toUpperCase());
        } catch (Exception IllegalArgumentException) {
            return "This resource type does not exist";
        }
        if ((userReceiver = User.getUserByUsername(options.get("u"))) != null) {
            int resourceAmount = Integer.parseInt(options.get("a"));
            int price = Integer.parseInt(options.get("p"));
            if (gameMap.getKingdomByOwner(currentUser).getBalance() >= price) {
//                if (gameMap.getKingdomByOwner(currentUser).getNumberOfStock(BuildingType.STOCKPILE) * StockType.STOCKPILE.getCAPACITY() >
//                        gameMap.getKingdomByOwner(currentUser).getResourceAmount(resourceType) + amount) {
                    String massageRequest = options.get("m");
                    Trade trade = new Trade(ResourceType.valueOf(options.get("t").toUpperCase()), resourceAmount, price, currentUser, userReceiver, massageRequest, Trade.countId);
                    Trade.countId++;
                    Trade.getTrades().add(trade);
                    gameMap.getKingdomByOwner(currentUser).addRequest(trade);
                    gameMap.getKingdomByOwner(currentUser).getHistoryTrade().add(trade);
                    gameMap.getKingdomByOwner(userReceiver).addSuggestion(trade);
                    gameMap.getKingdomByOwner(userReceiver).addNotification(trade);
                    return "The request was successfully registered";
//                } else
//                    return "You do not have enough space for this resource!";
            } else
                return "your balance not enough";
        } else
            return "Username with this ID was not found";
    }

    public String showTradeList() {
        StringBuilder output = new StringBuilder("your suggestions :");
        for (Trade trade : gameMap.getKingdomByOwner(currentUser).getMySuggestion()) {
            output.append("\nResource type : ").append(trade.getResourceType().name())
                    .append(" resource amount : ").append(trade.getResourceAmount())
                    .append(" price : ").append(trade.getPrice()).append(" from : ")
                    .append(trade.getUserSender().getUserName()).append(" id : ").append(trade.getId())
                    .append(" massage : ").append(trade.getMassageRequest());
        }
//        gameMap.getKingdomByOwner(currentUser).getNotification().clear();
        return output.toString();
    }

    public String tradeAccept(HashMap<String, String> options) {
        for (String key : options.keySet()) if (options.get(key) == null) return "Please input necessary options!";
        for (String key : options.keySet())
            if (options.get(key).equals("")) return "Illegal value. Please fill the options!";
        for (Trade trade : gameMap.getKingdomByOwner(currentUser).getMySuggestion()) {
            if (trade.getId() == Integer.parseInt(options.get("i"))) {
                Kingdom receiverkingdom = gameMap.getKingdomByOwner(currentUser);
                if (receiverkingdom.getResourceAmount(trade.getResourceType()) >= trade.getResourceAmount()) {
                    receiverkingdom.setResourceAmount(trade.getResourceType(), -1 * trade.getResourceAmount());
                    receiverkingdom.setBalance(receiverkingdom.getBalance() + trade.getPrice());
                    receiverkingdom.getHistoryTrade().add(trade);
                    receiverkingdom.getMySuggestion().remove(trade);
                    Kingdom senderKingdom = gameMap.getKingdomByOwner(trade.getUserSender());
                    senderKingdom.setResourceAmount(trade.getResourceType(), trade.getResourceAmount());
                    senderKingdom.setBalance(senderKingdom.getBalance() - trade.getPrice());
                    trade.setMassageAccept(options.get("m"));
                    senderKingdom.getNotification().add(trade);

                    return "The trade was successful";
                } else
                    return "The resource balance is not enough";
            }
        }
        return "This ID was not found for you";
    }

    public String showTradeHistory() {
        StringBuilder output = new StringBuilder("your history:");
        for (Trade trade : gameMap.getKingdomByOwner(currentUser).getHistoryTrade()) {
            output.append("\nResource type : ").append(trade.getResourceType().name())
                    .append("\nresource amount : ").append(trade.getResourceAmount())
                    .append("\nprice : ").append(trade.getPrice()).append("\nid: ")
                    .append(trade.getId()).append("\nmassage : ").append(trade.getMassageRequest());
            if (trade.getUserSender().equals(currentUser)) output.append("\n...Requested... ");
            else output.append(" ...Accepted... ");
        }
        return output.toString();
    }

    public String showNotification() {
        StringBuilder output = new StringBuilder("your new notification : ");
        for (Trade trade : gameMap.getKingdomByOwner(currentUser).getNotification()) {
            if (trade.getUserSender().equals(currentUser)) {
                output.append("\nyour request accepted by--> ").append(trade.getUserReceiver().getUserName()).
                        append("\nmassage: ").append(trade.getMassageAccept());
            } else {
                output.append("\nnew suggestion by--> ").append(trade.getUserSender().getUserName()).
                        append("\nmassage: ").append(trade.getMassageRequest());
            }
        }
        gameMap.getKingdomByOwner(currentUser).getNotification().clear();
        return output.toString();
    }
}
