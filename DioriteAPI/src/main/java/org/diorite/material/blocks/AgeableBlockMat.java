package org.diorite.material.blocks;

/**
 * Representing ageable block, most of implementations use ages from 0 to 15 or 7
 */
public interface AgeableBlockMat
{
    /**
     * @return age of block
     */
    int getAge();

    /**
     * Returns sub-type of block, based on age.
     *
     * @param age age og block
     *
     * @return sub-type of block
     */
    AgeableBlockMat getAge(int age);
}
