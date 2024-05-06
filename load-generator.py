import requests
import threading
import time

# test 3
# SLEEP = 18
# N_REQUESTS = 3
# URL = "http://149.156.182.229:30111/limited-threads/compute"

# test 2
SLEEP = 1
N_REQUESTS = 1
URL = "http://149.156.182.229:30111/test/get-data-external"

def send_request():
    try:
        start = time.time()
        res = requests.get(URL).content
        print(time.time() - start, res)
    except (KeyboardInterrupt, SystemExit) as e:
        raise e
    except Exception:
        pass

iter = 1
while True:
    for _ in range(N_REQUESTS):
        threading.Thread(target=send_request, daemon=True).start()
    iter += 1
    if iter % 100 == 0:
        N_REQUESTS = min(4, N_REQUESTS + 1)
    time.sleep(SLEEP)
