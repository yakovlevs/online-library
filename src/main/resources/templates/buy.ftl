<a id="buy-button" class="btn btn-primary pull-left"
   href="https://auth.robokassa.ru/Merchant/Index.aspx?MerchantLogin=${MerchantLogin}&OutSum=${book.getPrice()}&Description=${book.getTitle()}&SignatureValue=${book.getSignatureValue()}&IsTest=1&Shp_book=${book.getId()}&Shp_user=${username}"
>Buy</a>