package com.successfactors.library.client.helper;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.InvocationException;

//import edu.tongji.tjfood.client.TongjiFood;
//import edu.tongji.tjfood.client.event.RPCInEvent;
//import edu.tongji.tjfood.client.event.RPCOutEvent;


public abstract class RPCCall<T> implements AsyncCallback<T> {
	
  protected abstract void callService(AsyncCallback<T> cb);

  private void call(final int retriesLeft) {
    onRPCOut();

    callService(new AsyncCallback<T>() {
      public void onFailure(Throwable caught) {
        onRPCIn();
        
        GWT.log(caught.toString(), caught);
        try {
          throw caught;
        } catch (InvocationException invocationException) {
          if (retriesLeft <= 0) {
            RPCCall.this.onFailure(invocationException);
          } else {
            call(retriesLeft - 1); // retry call
          }
        }catch (Throwable e) {// application exception
          RPCCall.this.onFailure(e);
        }
      }

      public void onSuccess(T result) {
        onRPCIn();
        RPCCall.this.onSuccess(result);
      }
    });
  }

  private void onRPCIn() {
	  GWT.log("RPCCall:onRPCIn");
	  //TongjiFood.get().getEventBus().fireEvent(new RPCInEvent());
  }

  private void onRPCOut() {
	  GWT.log("RPCCall:onRPCOut");
	  //TongjiFood.get().getEventBus().fireEvent(new RPCOutEvent());
  }

  public void retry(int retryCount) {
    call(retryCount);
  }
}
