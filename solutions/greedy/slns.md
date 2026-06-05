
```java
boolean canJump(int[] nums) {

// Initially the final position is the last index
int finalPosition = nums. length - 1;

// Start with the second last index
for (int idx = nums. length - 2; idx >= 0; idx -- ) {

// If you can reach the final position from this index
// update the final position flag
if (idx + nums [idx] >= finalPosition) {
finalPosition = idx;

[ 1

}

}

// If we reach the first index, then we can
// make the jump possible
return finalPosition == 0;

}
```


jump gaem-2
int jump(int[] nums) {

int totalJumps = 0;

// destination is last index
int destination = nums. length - 1;

int coverage = 0, lastJumpIdx = 0;

// Base case
if (nums.length == 1) return 0;

// Greedy strategy: extend coverage as long as possible
for (int i= 0; i < nums. length; i++) {

coverage = Math.max(coverage, i + nums[i]);

if (i == lastJumpIdx) {
astJumpIdx = coverage;
totalJumps++;

// check if we reached destination already
if (coverage >= destination) {
return totalJumps;

DRY-

}

return totalJumps;

}