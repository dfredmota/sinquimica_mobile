package br.com.martinlabs.commons.android;

import android.app.Activity;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import br.com.martinlabs.commons.android.purchase.IabHelper;
import br.com.martinlabs.commons.android.purchase.Purchase;
import br.com.martinlabs.commons.android.purchase.SkuDetails;

/**
 * Created by gil on 5/17/16.
 */
public class MLPurchase {
    public interface ProcessarCompraDelegate { void doit (Purchase purchase); }
    public interface ProcessarConsumoDelegate { void doit(String token); }

    Activity _act;
    List<SkuDetails> _produtos = new ArrayList<>();
    IabHelper _iabHelper;

    String _publicKey;
    List<String> _productIds;
    String _itemType = "inapp";
    boolean _ehConsumivel = true;
    ProcessarCompraDelegate _processarCompra;
    ProcessarConsumoDelegate _processarConsumo;

    private static final int RESULT_CODE = 1923;

    public MLPurchase(
            Activity act,
            String publicKey,
            List<String> productIds,
            boolean ehProduto,
            boolean ehConsumivel,
            ProcessarCompraDelegate processarCompra)
    {
        _act = act;
        _publicKey = publicKey;
        _productIds = productIds;
        _itemType = ehProduto ? "inapp" : "subs";
        _ehConsumivel = ehConsumivel;
        _processarCompra = processarCompra;

        Init ();
    }

    void Init()
    {
        _iabHelper = new IabHelper(_act, _publicKey);
        Log.i("MLPurchaseManager:", "Service Connection: Iniciado (1)");

        _iabHelper.startSetup(result -> {
            Log.i("MLPurchaseManager:", "Service Connection: Iniciado (2)");


            carregarProdutosECompras();
        });
    }

    void carregarProdutosECompras()
    {
        try {
            _iabHelper.queryInventoryAsync(true, _productIds, null, (result, inv) -> {

                int countProducts = 0;
                for (String id : _productIds) {
                    SkuDetails details = inv.getSkuDetails(id);

                    if (details != null && details.getType().equals(_itemType)) {
                        countProducts++;

                        _produtos.add(inv.getSkuDetails(id));

                        Purchase p = inv.getPurchase(id);
                        Purchase purchase = null;
                        Log.i("MLPurchaseManager:", "Compras Pendente com ID: " + id);

                        if (p != null && p.getPurchaseState() == 0
                                && (_ehConsumivel || purchase == null || p.getPurchaseTime() > purchase.getPurchaseTime())) {
                            purchase = p;

                            //se é consumivel irá processar todas as compras
                            if (_ehConsumivel) {
                                _processarCompra.doit(purchase);
                            }
                        }

                        if (purchase != null) {
                            //se não é consumivel irá processar apenas a ultima compra
                            if (!_ehConsumivel) {
                                _processarCompra.doit(purchase);
                            }
                        }


                    }
                }

                if (countProducts == 0) {
                    Log.i("MLPurchaseManager:", "Suba para a loja para testar!!");
                } else {
                    Log.i("MLPurchaseManager:", "Produtos Carregados: " + countProducts);
                }
            });
        } catch (IabHelper.IabAsyncInProgressException e) {
            e.printStackTrace();
        }
    }

    public void consumirCompra(Purchase purchase, ProcessarConsumoDelegate processarConsumo)
    {
        _processarConsumo = processarConsumo;
        try {
            _iabHelper.consumeAsync(purchase, (purchase1, result) -> {
                String token = purchase1.getToken();
                Log.i("MLPurchaseManager:", "Compra Consumida, token: "+token);
                if (_processarConsumo != null) {
                    _processarConsumo.doit(token);
                }
            });
        } catch (IabHelper.IabAsyncInProgressException e) {
            e.printStackTrace();
        }
    }

    public void comprar(String productId)
    {
        if (_produtos == null || _produtos.isEmpty()) {
            Log.i("MLPurchaseManager:", "Tentativa de Compra: Nenhum produto");
            return;
        }

        SkuDetails product = null;
        for (SkuDetails p : _produtos) {
            if (p.getSku().equals(productId)) {
                product = p;
                break;
            }
        }

        if (product != null) {
            Log.i("MLPurchaseManager:", "Tentativa de Compra: Produto encontrado");

            try {
                _iabHelper.launchPurchaseFlow(_act, product.getSku(), RESULT_CODE, (result, info) -> {
                    carregarProdutosECompras();
                });
            } catch (IabHelper.IabAsyncInProgressException e) {
                e.printStackTrace();
            }
        } else {
            Log.i("MLPurchaseManager:", "Tentativa de Compra: Produto não encontrado");
        }
    }
}
